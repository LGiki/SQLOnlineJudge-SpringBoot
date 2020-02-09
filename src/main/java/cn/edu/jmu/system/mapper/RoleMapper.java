package cn.edu.jmu.system.mapper;

import cn.edu.jmu.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sgh
 * @since 2019-08-21
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * remark
     *
     * @param adminId adminId
     * @return List<Integer>
     */
    Integer selectRoleIdByAdminIdFromSysAdminRole(@Param("adminId") Integer adminId);

    Integer insertAdminIdAndRoleId(@Param("adminId") Integer adminId, @Param("roleId") Integer roleId);
}
