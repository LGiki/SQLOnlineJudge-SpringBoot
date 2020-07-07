package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.dto.AdminDto;
import cn.edu.jmu.system.mapper.AdminMapper;
import cn.edu.jmu.system.mapper.RoleMapper;
import cn.edu.jmu.system.service.AdminService;
import cn.edu.jmu.system.service.converter.AdminConverter;
import cn.edu.jmu.system.service.enums.UserStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Resource
    private RoleMapper roleMapper;

    /**
     * 得到所有管理员
     *
     * @param adminDto adminDto
     * @param page     page
     * @return IPage<admin>
     */
    @Override
    public IPage<AdminDto> getAll(AdminDto adminDto, Page page) {
        Page<Admin> adminPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Admin> iPage = baseMapper.selectPage(adminPage, predicate(adminDto));
        return iPage.convert(AdminConverter::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return baseMapper.deleteById(id) >= 1;
    }

    @Override
    public boolean insert(Admin admin) {
        baseMapper.insert(admin);
        Integer id = admin.getId();
        return roleMapper.insertAdminIdAndRoleId(id, 2) >= 1;
    }

    /**
     * 切换管理员状态：正常 <-> 正常
     *
     * @param id 管理员ID
     * @return boolean 操作是否执行成功
     */
    @Override
    public boolean toggleAdminStatus(Integer id) {
        Admin admin = baseMapper.selectById(id);
        if (admin.getStatus() == UserStatusEnum.NORMAL) {
            admin.setStatus(UserStatusEnum.LOCK);
        } else {
            admin.setStatus(UserStatusEnum.NORMAL);
        }
        return baseMapper.updateById(admin) >= 1;
    }

    /**
     * 条件构造器
     *
     * @param adminDto adminDto
     * @return LambdaQueryWrapper<admin>
     */
    private LambdaQueryWrapper<Admin> predicate(AdminDto adminDto) {
        if (adminDto == null) {
            return null;
        } else {
            LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
            if (adminDto.getId() != null) {
                queryWrapper.eq(Admin::getId, adminDto.getId());
                return queryWrapper;
            } else if (adminDto.getUsername() != null) {
                queryWrapper.like(Admin::getUsername, "%" + adminDto.getUsername() + "%");
            }
            return queryWrapper;
        }
    }
}
