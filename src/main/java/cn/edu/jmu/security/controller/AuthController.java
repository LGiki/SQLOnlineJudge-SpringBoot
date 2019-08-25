package cn.edu.jmu.security.controller;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.security.util.JwtTokenUtil;
import cn.edu.jmu.common.enums.LoginTypeEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sgh
 * @date 2019/8/21 下午3:38
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    /**
     * 用户登录
     *
     * @param username username
     * @param password password
     */
    @PostMapping(value = "/user/login")
    public ResponseEntity<BasicResponse> userLogin(@RequestParam(value = "username") String username,
                                                   @RequestParam(value = "password") String password) {
        // 得到当前 subject
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(username, password, LoginTypeEnum.USER.getType());
        BasicResponse basicResponse = new BasicResponse();
        subject.login(userToken);
        String token = JwtTokenUtil.generateToken(userToken);
        Map<String, String> data = new HashMap<>(1);
        data.put("token", token);
        basicResponse.wrapper(AbstractResponseCode.OK, "登录成功", data);
        return ResponseEntity.ok(basicResponse);
    }

    /**
     * 管理员登录
     *
     * @param username username
     * @param password password
     */
    @PostMapping(value = "/admin/login")
    public ResponseEntity<BasicResponse> adminLogin(@RequestParam(value = "username") String username,
                                                    @RequestParam(value = "password") String password) {
        // 得到当前 subject
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(username, password, LoginTypeEnum.ADMIN.getType());
        BasicResponse basicResponse = new BasicResponse();
        subject.login(userToken);
        String token = JwtTokenUtil.generateToken(userToken);
        Map<String, String> data = new HashMap<>(1);
        data.put("token", token);
        basicResponse.wrapper(AbstractResponseCode.OK, "登录成功", data);
        return ResponseEntity.ok(basicResponse);
    }
}
