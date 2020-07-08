package cn.edu.jmu.security.controller;

import cn.edu.jmu.common.enums.LoginTypeEnum;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.common.util.ValidateUtil;
import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.security.util.JwtTokenUtil;
import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.service.AdminService;
import cn.edu.jmu.system.service.RoleService;
import cn.edu.jmu.system.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
@RequestMapping(value = "/api")
public class AuthController {

    @Resource
    private UserService userService;

    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    /**
     * 用户登录
     */
    @PostMapping(value = "/user/auth")
    public ResponseEntity<BasicResponse> userLogin(@RequestParam String username, @RequestParam String password) {
        // 支持通过学号登录
        User userByStudentNo = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getStudentNo, username));
        if (userByStudentNo != null) {
            username = userByStudentNo.getUsername();
        }
        // 得到当前 subject
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(username, password, LoginTypeEnum.USER.getType());
        subject.login(userToken);
        String token = JwtTokenUtil.generateToken(userToken);
        Map<String, String> data = new HashMap<>(2);
        data.put("token", token);
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        data.put("id", user.getId().toString());
        return ResponseUtil.buildResponse("登录成功", data);
    }

    /**
     * 管理员登录
     */
    @PostMapping(value = "/admin/auth")
    public ResponseEntity<BasicResponse> adminLogin(@RequestParam String username, @RequestParam String password) {
        // 得到当前 subject
        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(username, password, LoginTypeEnum.ADMIN.getType());
        subject.login(userToken);
        String token = JwtTokenUtil.generateToken(userToken);
        Map<String, String> data = new HashMap<>(2);
        data.put("token", token);
        Admin admin = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getUsername, username));
        String roleName = roleService.findRoleByAdminId(admin.getId()).getRoleName();
        data.put("role", roleName);
        return ResponseUtil.buildResponse("登录成功", data);
    }

//    /**
//     * 用户注册
//     */
//    @PostMapping(value = "/user/register")
//    public ResponseEntity<BasicResponse> userRegister(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam Long studentNo) {
//        if (!ValidateUtil.isEmail(email)) {
//            return ResponseUtil.fail("注册失败，请检查邮箱格式是否正确！");
//        }
//        User byId = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
//        if (ObjectUtil.isNotNull(byId)) {
//            return ResponseUtil.fail("注册失败，该用户名已存在！");
//        }
//        User byEmail = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, email));
//        if (ObjectUtil.isNotNull(byEmail)) {
//            return ResponseUtil.fail("注册失败，该邮箱已存在！");
//        }
//        User byStudentNo = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getStudentNo, studentNo));
//        if (ObjectUtil.isNotNull(byStudentNo)) {
//            return ResponseUtil.fail("注册失败，该学号已存在！");
//        }
//        User user = new User();
//        user.setUsername(username);
//        String salt = EncryptUtil.generatorSalt();
//        user.setSalt(salt);
//        password = EncryptUtil.encryption(username, password, salt);
//        user.setPassword(password);
//        user.setEmail(email);
//        user.setStudentNo(studentNo);
//        boolean success = userService.save(user);
//        return ResponseUtil.buildResponse(success, "注册成功", "注册失败");
//    }
}
