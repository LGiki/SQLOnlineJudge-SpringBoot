package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.entity.Solution;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;
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
 * @date 2019/06/23 09:27
 */

@RestController
@RequiresRoles(value = {"admin"})
@RequestMapping("/api/solutions")
public class SolutionController {

    @Resource
    private SolutionService solutionService;

    /**
     * 查询所有解答
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<BasicResponse> selectAll(Solution solution,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        Page<Solution> page = new Page<>(pageNum, pageSize);
        IPage<Solution> iPage = solutionService.get(solution, page);
        basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", iPage);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 通过ID查询解答详情
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getSolutionById(@PathVariable("id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        Solution solution = solutionService.getById(id);
        basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", solution);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 添加解答
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insertSolution(@RequestBody @Validated Solution solution) {
        BasicResponse response = new BasicResponse();
        if (solution != null && solution.getId() == null) {
            if (solutionService.saveOrUpdate(solution)) {
                response.wrapper(AbstractResponseCode.OK, "新增解答成功", solution);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "新增解答失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * 获取解答数量
     */
    @GetMapping(value = "/count")
    public ResponseEntity<BasicResponse> count() {
        BasicResponse response = new BasicResponse();
        response.wrapper(AbstractResponseCode.OK, "获取数量成功", solutionService.count());
        return ResponseEntity.ok().body(response);
    }
}

