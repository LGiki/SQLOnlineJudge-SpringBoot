package cn.edu.jmu.system.mapper;

import cn.edu.jmu.system.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * remark
     *
     * @param adminId adminId
     * @return List<Integer>
     */
    List<Integer> selectAllRoleIdByAdminIdFromSysAdminRole(@Param("adminId") Integer adminId);

}
