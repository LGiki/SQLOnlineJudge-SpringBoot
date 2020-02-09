package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.api.CreateUserGroupRequest;
import cn.edu.jmu.system.api.CreateUserGroupResponse;
import cn.edu.jmu.system.entity.UserGroup;
import cn.edu.jmu.system.mapper.UserGroupMapper;
import cn.edu.jmu.system.service.UserGroupService;
import cn.edu.jmu.system.service.inverter.UserGroupInverter;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xeathen
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {
    @Resource
    UserGroupMapper userGroupMapper;

    @Override
    public CreateUserGroupResponse create(CreateUserGroupRequest request) {
        UserGroup userGroup = UserGroupInverter.userGroup(request);
        userGroupMapper.insert(userGroup);
        CreateUserGroupResponse response = new CreateUserGroupResponse();
        response.setId(userGroup.getId());
        return response;
    }
}
