package cn.edu.jmu.sqlonlinejudge.controller.user;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.entity.dto.UserDto;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.edu.jmu.sqlonlinejudge.service.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sgh
 * @date 2019/8/27 下午2:53
 */
@RestController
@RequiresRoles(value = {"user"})
@RequestMapping("/api/user/users")
public class UserSelfController {

    @Resource
    private UserService userService;

    /**
     * 获取登录用户的信息
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> get() {
        BasicResponse response = new BasicResponse();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        UserDto userDto = UserMapper.toDto(user);
        response.wrapper(AbstractResponseCode.OK, "查询成功", userDto);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 更改用户信息
     */
    @PutMapping(value = "/")
    public ResponseEntity<BasicResponse> update(@RequestBody UserDto userDto) {
        BasicResponse response = new BasicResponse();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (userDto.getId() != null && user.getId().equals(userDto.getId())) {
            userDto.setStatus(user.getStatus());
            if (userService.update(userDto)) {
                response.wrapper(AbstractResponseCode.OK, "更新成功", userDto);
                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
