package cn.edu.jmu.system.controller.user;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.judge.executor.ThreadPoolUtils;
import cn.edu.jmu.judge.executor.thread.JudgeThread;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.entity.dto.UserDto;
import cn.edu.jmu.system.service.SolutionService;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    @GetMapping(value = "users/{id}")
    public ResponseEntity<BasicResponse> get(@PathVariable(value = "id") Integer id) {
        User user = userService.getById(id);
        UserDto userDto = UserMapper.toDto(user);
        return ResponseUtil.buildResponse("查询成功", userDto);
    }

    /**
     * 更改用户信息
     */
    @PutMapping(value = "users/")
    public ResponseEntity<BasicResponse> update(@RequestBody UserDto userDto) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user.getId().equals(userDto.getId())) {
            userDto.setStatus(user.getStatus());
            boolean success = userService.update(userDto);
            return ResponseUtil.buildResponse(success, "更新成功", "更新失败");
        } else {
            return ResponseUtil.fail("无法更新他人信息");
        }
    }

    /**
     * 用户查看自己提交的代码
     */
    @GetMapping(value = "/solutions/{id}")
    public ResponseEntity<BasicResponse> getCode(@PathVariable(value = "id") Integer id) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Solution solution = solutionService.getById(id);
        if (solution.getUid().equals(user.getId())) {
            String code = solution.getSourceCode();
            return ResponseUtil.buildResponse(code);
        } else {
            return ResponseUtil.fail("无权限");
        }
    }


    /**
     * 用户提交判题
     *
     * @param solutionDto
     * @return ResponseEntity
     */
    @PostMapping(value = "/solutions/")
    public ResponseEntity<BasicResponse> submit(@RequestBody @Validated SolutionDto solutionDto) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        solutionDto.setUid(user.getId());
        boolean success = solutionService.add(solutionDto);
        return ResponseUtil.buildResponse(success, "提交成功", "提交失败");
    }
}
