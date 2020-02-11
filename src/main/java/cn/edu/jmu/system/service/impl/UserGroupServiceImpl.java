package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.api.CreateUserGroupRequest;
import cn.edu.jmu.system.api.CreateUserGroupResponse;
import cn.edu.jmu.system.api.SearchUserGroupResponse;
import cn.edu.jmu.system.entity.UserGroup;
import cn.edu.jmu.system.mapper.UserGroupMapper;
import cn.edu.jmu.system.service.UserGroupService;
import cn.edu.jmu.system.service.converter.UserGroupConverter;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xeathen
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {
    @Resource
    UserGroupMapper userGroupMapper;

    @Override
    public SearchUserGroupResponse search(Integer skip, Integer limit) {
        List<UserGroup> userGroups = userGroupMapper.list(skip, limit);
        SearchUserGroupResponse response = new SearchUserGroupResponse();
        response.setUserGroups(userGroups.stream().map(UserGroupConverter::userGroup).collect(Collectors.toList()));
        response.setTotal(userGroupMapper.count());
        return response;
    }

    @Override
    public CreateUserGroupResponse create(CreateUserGroupRequest request) {
        UserGroup userGroup = UserGroupConverter.userGroup(request);
        userGroupMapper.insert(userGroup);
        CreateUserGroupResponse response = new CreateUserGroupResponse();
        response.setId(userGroup.getId());
        return response;
    }
}