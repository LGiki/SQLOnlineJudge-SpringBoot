package cn.edu.jmu.sqlonlinejudge.service.mapper;

import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.entity.dto.UserDto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author sgh
 * @date 2019/8/26 下午3:54
 */
public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(user, userDto);
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtil.copyProperties(userDto, user, true, CopyOptions.create().setIgnoreNullValue(true));
        return user;
    }
}
