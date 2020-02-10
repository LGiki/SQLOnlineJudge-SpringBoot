package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.dto.AdminDto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author sgh
 * @date 2019/8/26 下午6:16
 */
public class AdminConverter {

    public static AdminDto toDto(Admin admin) {
        AdminDto adminDto = new AdminDto();
        BeanUtil.copyProperties(admin, adminDto);
        return adminDto;
    }

    public static Admin toEntity(cn.edu.jmu.system.entity.dto.AdminDto adminDto) {
        Admin admin = new Admin();
        BeanUtil.copyProperties(adminDto, admin, true, CopyOptions.create().setIgnoreNullValue(true));
        return admin;
    }
}
