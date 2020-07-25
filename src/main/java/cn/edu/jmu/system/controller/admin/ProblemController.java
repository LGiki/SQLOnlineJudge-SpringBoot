package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.enums.JudgeResponseCodeEnum;
import cn.edu.jmu.judge.service.JudgeService;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDetailDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.entity.dto.ProblemListDto;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.converter.ProblemConverter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@RestController
@RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
@RequestMapping("/api/admin/problems")
@Slf4j
public class ProblemController {

    @Resource
    private ProblemService problemService;

    @Resource
    private JudgeService judgeService;

    /**
     * 查询所有题目
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getAll(ProblemDto problemDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Problem> page = new Page<>(pageNum, pageSize);
        IPage<ProblemListDto> iPage = problemService.getAll(problemDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 通过ID查询题目详情
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getProblemById(@PathVariable("id") Integer id) {
        Problem problem = problemService.getById(id);
        ProblemDetailDto problemDetailDto = ProblemConverter.toDetail(problem);
        return ResponseUtil.buildResponse("查询成功", problemDetailDto);
    }

    /**
     * 通过ID删除题目
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable("id") Integer id) {
        return ResponseUtil.buildResponse(problemService.removeById(id), "删除题目成功", "删除题目失败");
    }

    /**
     * 添加题目
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insertProblem(@RequestBody @Validated ProblemDetailDto problemDetailDto) {
        Problem problem = ProblemConverter.toEntity(problemDetailDto);
        if (!problem.getIsUpdate()) {
            problem.setSelectAfterUpdate(null);
        }
        if (problemService.saveOrUpdate(problem)) {
            JudgeResultJson judgeResultJson = judgeService.getTrueResultMd5(problem.getAnswer(), problem.getDatabaseId());
            if ("0".equals(judgeResultJson.getCode())) {
                problem.setTrueResult(judgeResultJson.getData().getTrueResult());
                problemService.update(problem);
                return ResponseUtil.ok("新增题目成功");
            } else {
                problemService.removeById(problem.getId());
                return ResponseUtil.fail("给出的答案有误," + judgeResultJson.getMessage());
            }
        } else {
            problemService.removeById(problem.getId());
            return ResponseUtil.fail("新增题目失败");
        }
    }

    /**
     * 通过ID更新题目
     */
    @PutMapping(value = "/{problemId}")
    public ResponseEntity<BasicResponse> updateProblemById(@PathVariable("problemId") Integer problemId, @RequestBody @Validated ProblemDetailDto problemDetailDto) {
        if (!problemService.existById(problemId)) {
            return ResponseUtil.fail("题目ID不存在");
        }
        Problem problem = ProblemConverter.toEntity(problemDetailDto);
        problem.setId(problemId);
        if (!problem.getIsUpdate()) {
            problem.setSelectAfterUpdate(null);
        }
        // 更新数据库信息
        JudgeResultJson judgeResultJson = judgeService.getTrueResultMd5(problem.getAnswer(), problem.getDatabaseId());
        if ("0".equals(judgeResultJson.getCode())) {
            problem.setTrueResult(judgeResultJson.getData().getTrueResult());
            problemService.update(problem);
            return ResponseUtil.ok("更新题目信息成功");
        } else {
            return ResponseUtil.fail("给出的答案有误," + judgeResultJson.getMessage());
        }
    }

    /**
     * 获取题目数量
     */
    @GetMapping(value = "/count")
    public ResponseEntity<BasicResponse> count() {
        int count = problemService.count();
        return ResponseUtil.buildResponse(count);
    }

    @PostMapping(value = "/judgement/{id}")
    public ResponseEntity<BasicResponse> judge(@PathVariable(value = "id") Integer databaseId, @RequestBody SolutionDto solutionDto) {
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
