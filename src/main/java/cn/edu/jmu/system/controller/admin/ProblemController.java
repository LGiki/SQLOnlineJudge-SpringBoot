package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.judge.service.JudgeService;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDetailDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.mapper.ProblemMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@RestController
@RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
@RequestMapping("/api/admin/problems")
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
        return ResponseUtil.buildResponse("查询成功", problem);
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
        Problem problem = ProblemMapper.toEntity(problemDetailDto);
        if (problemService.saveOrUpdate(problem)) {
            String trueResult = judgeService.getTrueResult(problem.getId());
            if (trueResult != null) {
                problem.setTrueResult(trueResult);
                problemService.update(problem);
                return ResponseUtil.ok("新增题目成功");
            } else {
                return ResponseUtil.fail("给出的答案有误");
            }
        } else {
            return ResponseUtil.fail("新增题目失败");

        }


    }

    /**
     * 通过ID更新题目
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> updateProblemById(@PathVariable("id") Integer id, @RequestBody @Validated ProblemDetailDto problemDetailDto) {
        if (problemDetailDto.getId() != null && problemDetailDto.getId().equals(id)) {
            Problem problem = ProblemMapper.toEntity(problemDetailDto);
            // 更新数据库信息
            boolean success = problemService.update(problem);
            return ResponseUtil.buildResponse(success, "更新题目信息成功", "更新题目信息失败");
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
