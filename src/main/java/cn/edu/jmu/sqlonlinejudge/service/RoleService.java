package cn.edu.jmu.sqlonlinejudge.service;

import cn.edu.jmu.sqlonlinejudge.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
    List<Role> findAllRoleByAdminId(Integer adminId);
}
