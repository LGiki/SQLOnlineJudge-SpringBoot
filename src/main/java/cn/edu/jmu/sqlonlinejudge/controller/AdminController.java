package cn.edu.jmu.sqlonlinejudge.controller;


import cn.edu.jmu.sqlonlinejudge.config.common.UserToken;
import cn.edu.jmu.sqlonlinejudge.service.enums.LoginTypeEnum;
import cn.edu.jmu.sqlonlinejudge.service.enums.ResponseStatusEnum;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.util.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
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
 * 管理员控制器
 *
 * @author sgh
 * @since 2019-08-19
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    private JwtTokenUtil jwtTokenUtil;


    /**
     * 管理员登录
     *
     * @param username username
     * @param password password
     */
    @PostMapping(value = "/login")
    public ResponseEntity<BasicResponse> login(@RequestParam(value = "username") String username,
                                               @RequestParam(value = "password") String password) {
        // 得到当前 subject
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(username, password, LoginTypeEnum.ADMIN.getType());
        BasicResponse basicResponse = new BasicResponse();
        try {
            subject.login(userToken);
            String token = jwtTokenUtil.generateToken(userToken);
            Map<String, String> data = new HashMap<>(1);
            data.put("token", token);
            basicResponse.wrapper(ResponseStatusEnum.OK.getCode(), "登录成功", data);
            return ResponseEntity.ok(basicResponse);
        } catch (UnknownAccountException uae) {
            basicResponse.wrapper(ResponseStatusEnum.FAIL.getCode(), "未知账户");
        } catch (IncorrectCredentialsException ice) {
            basicResponse.wrapper(ResponseStatusEnum.FAIL.getCode(), "密码不正确!");
        } catch (AuthenticationException ae) {
            basicResponse.wrapper(ResponseStatusEnum.FAIL.getCode(), "用户名或密码不正确!");
        } catch (Exception e) {
            basicResponse.wrapper(ResponseStatusEnum.FAIL.getCode(), "未知错误");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(basicResponse);
    }

}
