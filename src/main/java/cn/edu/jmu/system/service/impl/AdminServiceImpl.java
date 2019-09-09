package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.dto.AdminDto;
import cn.edu.jmu.system.mapper.AdminMapper;
import cn.edu.jmu.system.service.AdminService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param adminDto adminDto
     * @param page     page
     * @return IPage<admin>
     */
    @Override
    public IPage<AdminDto> getAll(AdminDto adminDto, Page page) {
        Page<Admin> adminPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Admin> iPage = baseMapper.selectPage(adminPage, predicate(adminDto));
        return iPage.convert(cn.edu.jmu.system.service.mapper.AdminMapper::toDto);
    }

    /**
     * 更新管理员
     *
     * @param admin admin
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Admin admin) {
        if (admin.getPassword() != null) {
            admin.setPassword(EncryptUtil.encryption(admin.getUsername(), admin.getPassword(), admin.getSalt()));
        }
        Admin select = baseMapper.selectById(admin.getId());
        BeanUtil.copyProperties(admin, select, true,
                CopyOptions.create().setIgnoreNullValue(true)
                        .setIgnoreError(true)
                        .setIgnoreProperties("id", "username", "salt"));
        return baseMapper.updateById(select) >= 1;
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
