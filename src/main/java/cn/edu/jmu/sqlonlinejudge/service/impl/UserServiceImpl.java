package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.entity.dto.UserDto;
import cn.edu.jmu.sqlonlinejudge.entity.vo.UserVo;
import cn.edu.jmu.sqlonlinejudge.mapper.UserMapper;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
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
    public IPage<UserVo> getAll(UserDto userDto, Page page) {
        Page<User> userPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<User> iPage = baseMapper.selectPage(userPage, predicate(userDto));
        return iPage.convert(cn.edu.jmu.sqlonlinejudge.service.mapper.UserMapper::userToUserVo);
    }

    /**
     * 更新用户
     *
     * @param user user
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(User user) {
        if (user.getPassword() != null) {
            user.setPassword(EncryptUtil.encryption(user.getUsername(), user.getPassword(), user.getSalt()));
        }
        User select = baseMapper.selectById(user.getId());
        BeanUtil.copyProperties(user, select, true,
                CopyOptions.create().setIgnoreNullValue(true)
                        .setIgnoreError(true)
                        .setIgnoreProperties("id", "username", "salt", "submit", "solved"));
        return baseMapper.updateById(select) >= 1;
    }

    /**
     * 条件构造器
     *
     * @param userDto userDto
     * @return LambdaQueryWrapper<User>
     */
    private LambdaQueryWrapper<User> predicate(UserDto userDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (userDto == null) {
            return queryWrapper;
        } else {
            if (userDto.getId() != null) {
                queryWrapper.eq(User::getId, userDto.getId());
                return queryWrapper;
            }
            if (userDto.getUsername() != null) {
                queryWrapper.like(User::getUsername, "%" + userDto.getUsername() + "%");
            }
            if (userDto.getEmail() != null) {
                queryWrapper.like(User::getEmail, "%" + userDto.getEmail() + "%");
            }
            queryWrapper.orderByAsc(User::getId);
        }
        return queryWrapper;
    }
}
