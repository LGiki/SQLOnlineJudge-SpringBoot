package cn.edu.jmu.sqlonlinejudge.controller.user;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.entity.Solution;
import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.entity.dto.UserDto;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.edu.jmu.sqlonlinejudge.service.mapper.UserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Security;

/**
 * @author sgh
 * @date 2019/8/27 下午2:53
 */
@RestController
@RequiresRoles(value = {"user"})
@RequestMapping("/api/user/")
public class UserOperationController {

    @Resource
    private UserService userService;

    @Resource
    private SolutionService solutionService;

    /**
     * 获取登录用户的信息
     */
    @GetMapping(value = "users/")
    public ResponseEntity<BasicResponse> get(@RequestParam(value = "id") Integer id) {
        BasicResponse response = new BasicResponse();
        User user = userService.getById(id);
        UserDto userDto = UserMapper.toDto(user);
        response.wrapper(AbstractResponseCode.OK, "查询成功", userDto);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 更改用户信息
     */
    @PutMapping(value = "users/")
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

    /**
     * 用户查看自己提交的代码
     */
    @GetMapping(value = "/solutions/code")
    public ResponseEntity<BasicResponse> getCode(@RequestParam(value = "id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Solution solution = solutionService.getById(id);
        if (solution.getUid().equals(user.getId())) {
            basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", solution.getSourceCode());
            return ResponseEntity.ok(basicResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/solutions/")
    public ResponseEntity<BasicResponse> submit() {
        return null;
    }
}