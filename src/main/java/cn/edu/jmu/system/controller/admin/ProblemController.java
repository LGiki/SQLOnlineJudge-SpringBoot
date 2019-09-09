package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDetailDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.mapper.ProblemMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@RestController
@RequiresRoles(value = {"admin"})
@RequestMapping("/api/admin/problems")
public class ProblemController {

    @Resource
    private ProblemService problemService;

    /**
     * 查询所有题目
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<BasicResponse> selectAll(ProblemDto problemDto,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        Page<Problem> page = new Page<>(pageNum, pageSize);
        IPage<ProblemDto> iPage = problemService.getAll(problemDto, page);
        basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", iPage);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 通过ID查询题目详情
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getProblemById(@PathVariable("id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        Problem problem = problemService.getById(id);
        basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", problem);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 通过ID删除题目
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        // 删除题目
        if (problemService.removeById(id)) {
            response.wrapper(AbstractResponseCode.OK, "删除成功");
        } else {
            response.wrapper(AbstractResponseCode.FAIL, "删除失败");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 添加题目
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insertProblem(@RequestBody @Validated ProblemDetailDto problemDetailDto) {
        BasicResponse response = new BasicResponse();
        if (problemDetailDto != null && problemDetailDto.getId() == null) {
            Problem problem = ProblemMapper.toEntity(problemDetailDto);
            if (problemService.saveOrUpdate(problem)) {
                response.wrapper(AbstractResponseCode.OK, "新增题目成功", problem);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "新增题目失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 通过ID更新题目
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> updateProblemById(@PathVariable("id") Integer id,
                                                           @RequestBody ProblemDetailDto problemDetailDto) {
        BasicResponse response = new BasicResponse();
        if (problemDetailDto != null && problemDetailDto.getId() != null && problemDetailDto.getId().equals(id)) {
            Problem problem = ProblemMapper.toEntity(problemDetailDto);
            // 更新数据库信息
            if (problemService.update(problem)) {
                response.wrapper(AbstractResponseCode.OK, "更新题目信息成功", problem);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "更新题目信息失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 获取题目数量
     */
    @GetMapping(value = "/count")
    public ResponseEntity<BasicResponse> count() {
        BasicResponse response = new BasicResponse();
        response.wrapper(AbstractResponseCode.OK, "获取数量成功", problemService.count());
        return ResponseEntity.ok().body(response);
    }
}
