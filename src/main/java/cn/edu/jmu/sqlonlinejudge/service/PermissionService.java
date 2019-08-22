package cn.edu.jmu.sqlonlinejudge.service;

import cn.edu.jmu.sqlonlinejudge.entity.Permission;
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
public interface PermissionService extends IService<Permission> {

    /**
     * remark
     *
     * @param roleId roleId
     * @return List<Permission>
     */
    List<Permission> findAllPermissionByRoleId(Integer roleId);
}
