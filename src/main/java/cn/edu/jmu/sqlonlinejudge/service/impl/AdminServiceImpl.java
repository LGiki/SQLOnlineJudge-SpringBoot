package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.sqlonlinejudge.entity.Admin;
import cn.edu.jmu.sqlonlinejudge.mapper.AdminMapper;
import cn.edu.jmu.sqlonlinejudge.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sgh
 * @since 2019-08-19
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    /**
     * 得到所有管理员
     *
     * @param admin admin
     * @param page  page
     * @return IPage<User>
     */
    @Override
    public IPage<Admin> get(Admin admin, Page page) {
        Page<Admin> adminPage = new Page<>(page.getCurrent(), page.getSize());
        return baseMapper.selectPage(adminPage, predicate(admin));
    }

    /**
     * 条件构造器
     *
     * @param admin user
     * @return LambdaQueryWrapper<User>
     */
    private LambdaQueryWrapper<Admin> predicate(Admin admin) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (admin == null) {
            return queryWrapper;
        } else {
            if (admin.getId() != null) {
                queryWrapper.eq(Admin::getId, admin.getId());
                return queryWrapper;
            }
            if (admin.getUsername() != null) {
                queryWrapper.like(Admin::getUsername, "%" + admin.getUsername() + "%");
            }
        }
        return queryWrapper;
    }
}
