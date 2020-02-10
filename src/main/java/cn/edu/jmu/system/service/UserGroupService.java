package cn.edu.jmu.system.service;

import cn.edu.jmu.system.api.CreateUserGroupRequest;
import cn.edu.jmu.system.api.CreateUserGroupResponse;
import cn.edu.jmu.system.api.SearchUserGroupResponse;
import cn.edu.jmu.system.entity.UserGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xeathen
 */
public interface UserGroupService extends IService<UserGroup> {
    /**
     * @param skip
     * @param limit
     * @return
     */
    SearchUserGroupResponse search(Integer skip, Integer limit);

    /**
     * 创建用户组
     *
     * @return userGroupId
     */
    CreateUserGroupResponse create(CreateUserGroupRequest request);
}
