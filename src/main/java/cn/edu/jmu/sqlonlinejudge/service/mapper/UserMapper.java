package cn.edu.jmu.sqlonlinejudge.service.mapper;

import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.entity.dto.UserDto;
import cn.edu.jmu.sqlonlinejudge.entity.vo.UserVo;
import cn.hutool.core.bean.BeanUtil;

/**
 * @author sgh
 * @date 2019/8/26 下午3:54
 */
public class UserMapper {

    public static UserVo userToUserVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user, userVo);
        return userVo;
    }

    public static User userDtoToUser(UserDto userDto) {
        User user = new User();
        BeanUtil.copyProperties(userDto, user);
        return user;
    }
}
