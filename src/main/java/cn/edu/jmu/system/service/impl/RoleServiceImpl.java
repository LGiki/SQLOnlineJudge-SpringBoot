package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.Role;
import cn.edu.jmu.system.mapper.RoleMapper;
import cn.edu.jmu.system.service.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Role> findAllRoleByAdminId(Integer adminId) {
        List<Integer> roleIdList = baseMapper.selectAllRoleIdByAdminIdFromSysAdminRole(adminId);
        if (ObjectUtils.isEmpty(roleIdList)) {
            return null;
        } else {
            return baseMapper.selectBatchIds(roleIdList);
        }
    }
}
