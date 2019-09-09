package cn.edu.jmu.security.controller;

import cn.edu.jmu.common.enums.LoginTypeEnum;
import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.common.util.ValidateUtil;
import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.security.util.JwtTokenUtil;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.UserDto;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.mapper.UserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sgh
 * @date 2019/8/21 下午3:38
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping(value = "/user/login")
    public ResponseEntity<BasicResponse> userLogin(@RequestParam(value = "username") String username,
                                                   @RequestParam(value = "password") String password) {
        // 得到当前 subject
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(username, password, LoginTypeEnum.USER.getType());
        BasicResponse response = new BasicResponse();
        subject.login(userToken);
        String token = JwtTokenUtil.generateToken(userToken);
        Map<String, String> data = new HashMap<>(1);
        data.put("token", token);
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        data.put("id", user.getId().toString());
        response.wrapper(AbstractResponseCode.OK, "登录成功", data);
        return ResponseEntity.ok(response);
    }

    /**
     * 管理员登录
     */
    @PostMapping(value = "/admin/login")
    public ResponseEntity<BasicResponse> adminLogin(@RequestParam(value = "username") String username,
                                                    @RequestParam(value = "password") String password) {
        // 得到当前 subject
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(username, password, LoginTypeEnum.ADMIN.getType());
        BasicResponse response = new BasicResponse();
        subject.login(userToken);
        String token = JwtTokenUtil.generateToken(userToken);
        Map<String, String> data = new HashMap<>(1);
        data.put("token", token);
        response.wrapper(AbstractResponseCode.OK, "登录成功", data);
        return ResponseEntity.ok(response);
    }

    /**
     * 用户注册
     */
    @PostMapping(value = "/user/register")
    public ResponseEntity<BasicResponse> userRegister(@RequestParam(value = "username") String username,
                                                      @RequestParam(value = "password") String password,
                                                      @RequestParam(value = "email") String email) {
        BasicResponse response = new BasicResponse();
        if (!ValidateUtil.isEmail(email)) {
            response.wrapper(AbstractResponseCode.FAIL, "注册失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            User user = new User();
            user.setUsername(username);
            String salt = EncryptUtil.generatorSalt();
            user.setSalt(salt);
            password = EncryptUtil.encryption(username, password, salt);
            user.setPassword(password);
            user.setEmail(email);
            if (userService.save(user)) {
                UserDto userDto = UserMapper.toDto(user);
                response.wrapper(AbstractResponseCode.OK, "注册成功", userDto);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "注册失败");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
    }


}
