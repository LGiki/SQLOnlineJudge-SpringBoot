package cn.edu.jmu.system.mapper;

import cn.edu.jmu.system.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sgh
 * @since 2019-08-21
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * remark
     *
     * @param roleId roleId
     * @return List<Integer>
     */
    List<Integer> selectAllPermissionIdByRoleIdFromSysRolePermission(@Param("roleId") Integer roleId);
}
