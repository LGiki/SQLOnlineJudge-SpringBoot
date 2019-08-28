package cn.edu.jmu.sqlonlinejudge.controller.admin;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.entity.Solution;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sgh
 * @date 2019/08/28 13:34:54
 */
@RestController
@RequiresRoles(value = {"admin"})
@RequestMapping(value = "/api/admin/solutions")
public class SolutionController {

    @Resource
    private SolutionService solutionService;

    /**
     * 管理员查看提交的源代码
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getCode(@PathVariable(value = "id") Integer id) {
        BasicResponse response = new BasicResponse();
        Solution solution = solutionService.getById(id);
        response.wrapper(AbstractResponseCode.OK, "查询成功", solution.getSourceCode());
        return ResponseEntity.ok(response);
    }
}