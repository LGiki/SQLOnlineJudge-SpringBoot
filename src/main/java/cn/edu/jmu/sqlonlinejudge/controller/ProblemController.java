package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.sqlonlinejudge.model.Problem;
import cn.edu.jmu.sqlonlinejudge.service.ProblemService;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@RestController
@RequestMapping("/api/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    /**
     * 查询所有题目
     *
     * @param pageNum  当前页码
     * @param pageSize 页面数据条数
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BasicResponse selectAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            PageHelper.startPage(pageNum, pageSize);
            basicResponse.set(200, null, new PageInfo<>(problemService.selectAll()));
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 通过ID查询题目详情
     *
     * @param id 题目ID
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BasicResponse selectProblemById(@PathVariable("id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            Problem problem = problemService.selectById(id);
            if (problem != null) {
                basicResponse.set(200, null, problem);
            } else {
                basicResponse.set(400, "无此题目", null);
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

}
