package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.entity.Solution;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@RestController
@RequestMapping("/api/solutions")
public class SolutionController {

    @Resource
    private SolutionService solutionService;

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

