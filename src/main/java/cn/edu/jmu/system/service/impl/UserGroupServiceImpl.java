package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.api.usergourp.CreateUserGroupRequest;
import cn.edu.jmu.system.api.usergourp.CreateUserGroupResponse;
import cn.edu.jmu.system.api.usergourp.DeleteUserGroupResponse;
import cn.edu.jmu.system.api.usergourp.SearchUserGroupResponse;
import cn.edu.jmu.system.api.usergourp.UpdateUserGroupRequest;
import cn.edu.jmu.system.api.usergourp.UpdateUserGroupResponse;
import cn.edu.jmu.system.entity.UserGroup;
import cn.edu.jmu.system.entity.dto.UserGroupDto;
import cn.edu.jmu.system.mapper.UserGroupMapper;
import cn.edu.jmu.system.service.UserGroupService;
import cn.edu.jmu.system.service.converter.UserGroupConverter;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public IPage<UserGroupDto> search(UserGroupDto userGroupDto, Page page) {
        Page<UserGroup> userGroupPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<UserGroup> iPage = baseMapper.selectPage(userGroupPage, predicate(userGroupDto));
        return iPage.convert(UserGroupConverter::userGroupDto);
    }

    private Wrapper<UserGroup> predicate(UserGroupDto userGroupDto) {
        if (userGroupDto == null) {
            return null;
        } else {
            LambdaQueryWrapper<UserGroup> queryWrapper = new LambdaQueryWrapper<>();
            if (userGroupDto.getId() != null) {
                queryWrapper.eq(UserGroup::getId, userGroupDto.getId());
                return queryWrapper;
            } else if (userGroupDto.getName() != null) {
                queryWrapper.like(UserGroup::getName, "%" + userGroupDto.getName() + "%");
            }
            return queryWrapper;
        }
    }

    @Override
    public CreateUserGroupResponse create(CreateUserGroupRequest request) {
        UserGroup userGroup = UserGroupConverter.userGroup(request);
        userGroupMapper.insert(userGroup);
        CreateUserGroupResponse response = new CreateUserGroupResponse();
        response.setId(userGroup.getId());
        return response;
    }

    @Override
    public UpdateUserGroupResponse update(Integer id, UpdateUserGroupRequest request) {
        UserGroup userGroup = UserGroupConverter.userGroup(request);
        userGroup.setId(id);
        userGroupMapper.updateById(userGroup);
        UpdateUserGroupResponse response = new UpdateUserGroupResponse();
        response.setId(id);
        return response;
    }

    @Override
    public DeleteUserGroupResponse delete(Integer id) {
        userGroupMapper.deleteById(id);
        DeleteUserGroupResponse response = new DeleteUserGroupResponse();
        response.setId(id);
        return response;
    }
}
