package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.UserDto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author sgh
 * @date 2019/8/26 下午3:54
 */
public class UserConverter {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(user, userDto);
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtil.copyProperties(userDto, user, true, CopyOptions.create()
            .setIgnoreNullValue(true)
            .setIgnoreProperties("id", "submit", "solved"));
        return user;
    }

    public static void toEntity(UserDto userDto, User user) {
        if (user == null) {
            user = new User();
        }
        BeanUtil.copyProperties(userDto, user, true, CopyOptions.create()
            .setIgnoreNullValue(true)
            .setIgnoreProperties("id", "username", "submit", "solved"));
    }

    public static void toDto(User user, UserDto userDto) {
        if (userDto == null) {
            userDto = new UserDto();
        }
        BeanUtil.copyProperties(user, userDto);
    }
}
