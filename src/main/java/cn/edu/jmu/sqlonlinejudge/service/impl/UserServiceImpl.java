package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.sqlonlinejudge.entity.User;
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
     * @param user user
     * @param page page
     * @return IPage<User>
     */
    @Override
    public IPage<User> get(User user, Page page) {
        Page<User> userPage = new Page<>(page.getCurrent(), page.getSize());
        return baseMapper.selectPage(userPage, predicate(user));
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
                        .setIgnoreProperties("id", "username", "salt", ""));
        return baseMapper.updateById(select) >= 1;
    }

    /**
     * 条件构造器
     *
     * @param user user
     * @return LambdaQueryWrapper<User>
     */
    private LambdaQueryWrapper<User> predicate(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (user == null) {
            return queryWrapper;
        } else {
            if (user.getId() != null) {
                queryWrapper.eq(User::getId, user.getId());
                return queryWrapper;
            }
            if (user.getUsername() != null) {
                queryWrapper.like(User::getUsername, "%" + user.getUsername() + "%");
            }
            if (user.getEmail() != null) {
                queryWrapper.like(User::getUsername, "%" + user.getUsername() + "%");
            }
            queryWrapper.orderByAsc(User::getId);
        }
        return queryWrapper;
    }
}
