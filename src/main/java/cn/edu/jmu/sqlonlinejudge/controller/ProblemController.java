package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.sqlonlinejudge.service.ProblemService;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@RestController
@RequestMapping("/api/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BasicResponse selectAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            PageHelper.startPage(pageNum, pageSize);
            basicResponse.set(200, null, new PageInfo<>(problemService.selectAll()));
        }catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

}
