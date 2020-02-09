package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.service.JudgeService;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDetailDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.inverter.ProblemInverter;
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
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<BasicResponse> selectAll(ProblemDto problemDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Problem> page = new Page<>(pageNum, pageSize);
        IPage<ProblemDto> iPage = problemService.getAll(problemDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 通过ID查询题目详情
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getProblemById(@PathVariable("id") Integer id) {
        Problem problem = problemService.getById(id);
        ProblemDetailDto problemDetailDto = ProblemInverter.toDetail(problem);
        return ResponseUtil.buildResponse("查询成功", problemDetailDto);
    }

    /**
     * 通过ID删除题目
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable("id") Integer id) {
        // 删除题目
        boolean success = problemService.removeById(id);
        return ResponseUtil.buildResponse(success, "删除题目成功", "删除题目失败");
    }

    /**
     * 添加题目
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insertProblem(@RequestBody @Validated ProblemDetailDto problemDetailDto) {
        Problem problem = ProblemInverter.toEntity(problemDetailDto);
        if (problemService.saveOrUpdate(problem)) {
            log.debug(problem.toString());
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
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> updateProblemById(@PathVariable("id") Integer id, @RequestBody @Validated ProblemDetailDto problemDetailDto) {
        if (problemService.getById(id) == null) {
            return ResponseUtil.fail("题目ID不存在");
        }
        if (problemDetailDto.getId() != null && problemDetailDto.getId().equals(id)) {
            Problem problem = ProblemInverter.toEntity(problemDetailDto);
            // 更新数据库信息
            JudgeResultJson judgeResultJson = judgeService.getTrueResultMd5(problem.getAnswer(), problem.getDatabaseId());
            if ("0".equals(judgeResultJson.getCode())) {
                problem.setTrueResult(judgeResultJson.getData().getTrueResult());
                problemService.update(problem);
                return ResponseUtil.ok("更新题目信息成功");
            } else {
                return ResponseUtil.fail("给出的答案有误," + judgeResultJson.getMessage());
            }
        } else {
            return ResponseUtil.fail("id不一致");
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
}
