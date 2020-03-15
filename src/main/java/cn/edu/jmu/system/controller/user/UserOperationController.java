package cn.edu.jmu.system.controller.user;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.enums.JudgeResponseCodeEnum;
import cn.edu.jmu.judge.service.JudgeService;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.entity.dto.UserDto;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.SolutionService;
import cn.edu.jmu.system.service.UserProblemService;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.converter.UserConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sgh
 * @date 2019/8/27 下午2:53
 */
@RestController
@RequiresRoles(value = {"user"})
@RequestMapping("/api/user")
@Slf4j
public class UserOperationController {

    @Resource
    private UserService userService;

    @Resource
    private SolutionService solutionService;

    @Resource
    private UserProblemService userProblemService;

    @Resource
    private JudgeService judgeService;

    @Resource
    private ProblemService problemService;

    /**
     * 获取登录用户的信息
     */
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<BasicResponse> get(@PathVariable(value = "id") Integer id) {
        User user = userService.getById(id);
        UserDto userDto = UserConverter.toDto(user);
        return ResponseUtil.buildResponse("查询成功", userDto);
    }

    /**
     * 更改用户信息
     */
    @PutMapping(value = "/users/")
    public ResponseEntity<BasicResponse> update(@RequestBody UserDto userDto) {
        if (userDto.getId() == null) {
            return ResponseUtil.fail("用户id不能为空");
        }
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user.getId().equals(userDto.getId())) {
            userDto.setStatus(user.getStatus());
            userDto.setUsername(user.getUsername());
            userDto.setStudentNo(user.getStudentNo());
            userDto.setSolved(user.getSolved());
            userDto.setSubmit(user.getSubmit());
            userDto.setEmail(user.getEmail());
            boolean success = userService.update(userDto);
            return ResponseUtil.buildResponse(success, "修改成功", "修改失败");
        } else {
            return ResponseUtil.fail("无法修改他人信息");
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
        if (!ObjectUtils.isEmpty(solution) && solution.getUid().equals(user.getId())) {
//            SolutionCodeDto solutionCodeDto = SolutionConverter.toSolutionCodeDto(solution);
            return ResponseUtil.buildResponse(solution);
        } else {
            return ResponseUtil.fail("无权限");
        }
    }

    /**
     * 用户提交判题
     */
    @PostMapping(value = "/solutions/")
    public ResponseEntity<BasicResponse> submit(@RequestBody @Validated SolutionDto solutionDto) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        solutionDto.setUid(user.getId());
        Integer solutionId = solutionService.add(solutionDto);
        Map<String, Integer> data = new HashMap<>();
        data.put("solutionId", solutionId);
        return ResponseUtil.buildResponse(data);
    }

    /**
     * 得到用户通过题目和尝试题目的集合
     */
    @GetMapping(value = "/problems")
    public ResponseEntity<BasicResponse> getProblemStatus() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Map<String, List> data = new HashMap<>();
        data.put("accept", userProblemService.findByUidAndState(user.getId(), true));
        data.put("try", userProblemService.findByUidAndState(user.getId(), false));
        return ResponseUtil.buildResponse(data);
    }

    @PostMapping(value = "/judgement")
    public ResponseEntity<BasicResponse> judge(@RequestBody @Validated SolutionDto solutionDto) {
        Integer databaseId = problemService.getById(solutionDto.getPid()).getDatabaseId();
        JudgeResultJson result = PythonJudgeUtil.getTrueResult(solutionDto.getSourceCode(), databaseId);
        log.debug(result.toString());
        if (JudgeResponseCodeEnum.OK.getValue().equals(result.getCode())) {
            return ResponseUtil.buildResponse("执行成功", result.getData().getTrueResult());
        } else if (JudgeResponseCodeEnum.FAIL.getValue().equals(result.getCode())) {
            return ResponseUtil.fail("执行失败," + result.getMessage());
        } else if (JudgeResponseCodeEnum.NO_DB_FILE.getValue().equals(result.getCode())) {
            return ResponseUtil.fail("系统错误," + result.getMessage());
        }
        return ResponseUtil.fail("未知错误");
    }
}
