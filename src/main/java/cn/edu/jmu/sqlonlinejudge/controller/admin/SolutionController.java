package cn.edu.jmu.sqlonlinejudge.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    public ResponseEntity<BasicResponse> getCode(@RequestParam Integer id) {
        return null;
    }
}
