package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.Permission;
import cn.edu.jmu.system.mapper.PermissionMapper;
import cn.edu.jmu.system.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sgh
 * @since 2019-08-21
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    /**
     * remark
     *
     * @param roleId roleId
     * @return List<Permission>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Permission> findAllPermissionByRoleId(Integer roleId) {
        List<Integer> roleIdList = baseMapper.selectAllPermissionIdByRoleIdFromSysRolePermission(roleId);
        if (ObjectUtils.isEmpty(roleIdList)) {
            return null;
        } else {
            return baseMapper.selectBatchIds(roleIdList);
        }
    }
}
