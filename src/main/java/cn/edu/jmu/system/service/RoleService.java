package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sgh
 * @since 2019-08-21
 */
public interface RoleService extends IService<Role> {

    /**
     * remark
     *
     * @param adminId adminId
     * @return List<Role>
     */
    Role findRoleByAdminId(Integer adminId);
}
