package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.sqlonlinejudge.config.common.UserToken;
import cn.edu.jmu.sqlonlinejudge.model.User;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.edu.jmu.sqlonlinejudge.service.enums.LoginTypeEnum;
import cn.edu.jmu.sqlonlinejudge.service.enums.ResponseStatusEnum;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.util.JwtTokenUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author sgh
 * @date 2019/6/18 19:17
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 用户登录
     *
     * @param username username
     * @param password password
     */
    @PostMapping(value = "/login")
    public ResponseEntity<BasicResponse> login(@RequestParam(value = "username") String username,
                                               @RequestParam(value = "password") String password) {
        // 得到当前 subject
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(username, password, LoginTypeEnum.USER.getType());
        userToken.setRememberMe(true);
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

    /**
     * 查询所有用户
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        Page<User> userPage = new Page<>(pageNum, pageSize);
        IPage<User> iPage = userService.page(userPage, null);
        basicResponse.wrapper(ResponseStatusEnum.OK.getCode(), "查询成功", iPage);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 通过ID查询用户详情
     *
     * @param id 用户ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity selectUserById(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        User user = userService.getById(id);
        return ResponseEntity.ok().body(user);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param id 用户ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        // 删除用户
        userService.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 更新用户
     *
     * @param user 新的用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody User user, @PathVariable(value = "id") Integer id) {
        // 更新用户信息
        userService.saveOrUpdate(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 添加用户
     *
     * @param user 新的用户
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity insert(@RequestBody User user) {
        // 创建用户
        userService.saveOrUpdate(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
