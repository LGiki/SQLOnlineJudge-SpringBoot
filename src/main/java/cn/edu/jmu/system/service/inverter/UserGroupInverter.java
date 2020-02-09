package cn.edu.jmu.system.service.inverter;

import cn.edu.jmu.system.api.CreateUserGroupRequest;
import cn.edu.jmu.system.entity.UserGroup;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author xeathen
 */
public class UserGroupInverter {

    public static UserGroup userGroup(CreateUserGroupRequest dto) {
        UserGroup userGroup = new UserGroup();
        BeanUtil.copyProperties(dto, userGroup, true, CopyOptions.create()
            .setIgnoreNullValue(true)
            .setIgnoreProperties("id"));
        return userGroup;
    }
}
