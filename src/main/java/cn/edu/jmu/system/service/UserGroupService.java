package cn.edu.jmu.system.service;

import cn.edu.jmu.system.api.CreateUserGroupRequest;
import cn.edu.jmu.system.api.CreateUserGroupResponse;
import cn.edu.jmu.system.api.DeleteUserGroupResponse;
import cn.edu.jmu.system.api.SearchUserGroupResponse;
import cn.edu.jmu.system.api.UpdateUserGroupRequest;
import cn.edu.jmu.system.api.UpdateUserGroupResponse;
import cn.edu.jmu.system.entity.UserGroup;
import cn.edu.jmu.system.entity.dto.UserGroupDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xeathen
 */
public interface UserGroupService extends IService<UserGroup> {
    /**
     * 搜索用户组
     *
     * @param skip
     * @param limit
     * @return
     */
    SearchUserGroupResponse search(Integer skip, Integer limit);

    /**
     * 获取所有用户组
     *
     * @param userGroupDto userGroupDto
     * @param page         page
     * @return
     */
    IPage<UserGroupDto> getAll(UserGroupDto userGroupDto, Page page);

    /**
     * 创建用户组
     *
     * @return userGroupId
     */
    CreateUserGroupResponse create(CreateUserGroupRequest request);

    /**
     * 更新用户组信息
     *
     * @param id
     * @param request
     * @return
     */
    UpdateUserGroupResponse update(Integer id, UpdateUserGroupRequest request);

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    DeleteUserGroupResponse delete(Integer id);
}
