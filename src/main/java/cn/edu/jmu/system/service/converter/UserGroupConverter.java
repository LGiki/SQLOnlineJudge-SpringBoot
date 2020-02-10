package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.api.CreateUserGroupRequest;
import cn.edu.jmu.system.api.SearchUserGroupResponse;
import cn.edu.jmu.system.entity.UserGroup;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author xeathen
 */
public class UserGroupConverter {

    public static UserGroup userGroup(CreateUserGroupRequest request) {
        UserGroup userGroup = new UserGroup();
        BeanUtil.copyProperties(request, userGroup, true, CopyOptions.create()
            .setIgnoreNullValue(true)
            .setIgnoreProperties("id"));
        return userGroup;
    }

    public static SearchUserGroupResponse.UserGroup userGroup(UserGroup userGroup) {
        SearchUserGroupResponse.UserGroup ug = new SearchUserGroupResponse.UserGroup();
        BeanUtil.copyProperties(userGroup, ug, true, CopyOptions.create()
            .setIgnoreNullValue(true));
        return ug;
    }
}
