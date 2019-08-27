package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.entity.dto.UserDto;
import cn.edu.jmu.sqlonlinejudge.mapper.UserMapper;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
    public IPage<UserDto> getAll(UserDto userDto, Page page) {
        Page<User> userPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<User> iPage = baseMapper.selectPage(userPage, predicate(userDto));
        return iPage.convert(cn.edu.jmu.sqlonlinejudge.service.mapper.UserMapper::toDto);
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
        cn.edu.jmu.sqlonlinejudge.service.mapper.UserMapper.toEntity(userDto, user);
        return baseMapper.updateById(user) >= 1;
    }

    /**
     * 条件构造器
     *
     * @param userDto userDto
     * @return LambdaQueryWrapper<User>
     */
    private LambdaQueryWrapper<User> predicate(UserDto userDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(User::getSolved);
        if (userDto == null) {
            return queryWrapper;
        } else {
            if (userDto.getId() != null) {
                queryWrapper.eq(User::getId, userDto.getId());
            } else if (userDto.getUsername() != null) {
                queryWrapper.like(User::getUsername, "%" + userDto.getUsername() + "%");
            } else if (userDto.getEmail() != null) {
                queryWrapper.like(User::getEmail, "%" + userDto.getEmail() + "%");
            }
            if (userDto.getStatus() != null) {
                queryWrapper.eq(User::getStatus, userDto.getStatus());
            }
            return queryWrapper;
        }
    }
}
