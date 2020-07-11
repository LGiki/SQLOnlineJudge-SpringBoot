package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.UserDto;
import cn.edu.jmu.system.mapper.UserMapper;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.converter.UserConverter;
import cn.edu.jmu.system.service.enums.UserStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sgh
 * @date 2019/8/19 下午7:09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 得到所有用户
     *
     * @param userDto userDto
     * @param page    page
     * @return IPage<User>
     */
    @Override
    public IPage<UserDto> getAll(UserDto userDto, Page page, SFunction<User, ?> column, Boolean orderByDesc) {
        Page<User> userPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<User> iPage = baseMapper.selectPage(userPage, predicate(userDto, column, orderByDesc));
        return iPage.convert(UserConverter::toDto);
    }

    /**
     * 更新用户
     *
     * @param userDto userDto
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(UserDto userDto) {
        User user = baseMapper.selectById(userDto.getId());
        if (userDto.getPassword() != null) {
            userDto.setPassword(EncryptUtil.encryption(userDto.getUsername(), userDto.getPassword(), user.getSalt()));
        }
        UserConverter.toEntity(userDto, user);
        return baseMapper.updateById(user) >= 1;
    }

    /**
     * 更改用户状态
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean changeUserStatus(Integer id) {
        User user = baseMapper.selectById(id);
        if (user.getStatus() == UserStatusEnum.NORMAL) {
            user.setStatus(UserStatusEnum.LOCK);
        } else {
            user.setStatus(UserStatusEnum.NORMAL);
        }
        return baseMapper.updateById(user) >= 1;
    }

    @Override
    public Boolean existById(Integer id) {
        return baseMapper.selectCount(Wrappers.<User>lambdaQuery().eq(User::getId, id)) != 0;
    }

    /**
     * 条件构造器
     *
     * @param userDto userDto
     * @return LambdaQueryWrapper<User>
     */
    private LambdaQueryWrapper<User> predicate(UserDto userDto, SFunction<User, ?> column, Boolean orderByDesc) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (orderByDesc) {
            queryWrapper.orderByDesc(column);
        } else {
            queryWrapper.orderByAsc(column);
        }
        if (userDto == null) {
            return queryWrapper;
        } else {
            if (userDto.getId() != null) {
                queryWrapper.eq(User::getId, userDto.getId());
            } else if (userDto.getUsername() != null) {
                queryWrapper.like(User::getUsername, "%" + userDto.getUsername() + "%");
            } else if (userDto.getEmail() != null) {
                queryWrapper.like(User::getEmail, "%" + userDto.getEmail() + "%");
            } else if (userDto.getStudentNo() != null) {
                queryWrapper.like(User::getStudentNo, "%" + userDto.getStudentNo() + "%");
            }
            if (userDto.getStatus() != null) {
                queryWrapper.eq(User::getStatus, userDto.getStatus());
            }
            return queryWrapper;
        }
    }
}
