package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.sqlonlinejudge.model.Solution;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@RestController
@RequestMapping("/api/solution")
public class SolutionController {
    @Autowired
    private SolutionService solutionService;

    /**
     * 查询所有提交
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
            basicResponse.set(200, null, new PageInfo<>(solutionService.selectAllOrderBySubmitTimeDesc()));
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 通过ID查询提交详情
     *
     * @param id 提交ID
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BasicResponse selectSolutionById(@PathVariable("id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            Solution solution = solutionService.selectById(id);
            if (solution != null) {
                basicResponse.set(200, null, solution);
            } else {
                basicResponse.set(400, "无此提交");
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 通过ID删除提交
     *
     * @param id 提交ID
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BasicResponse deleteSolutionById(@PathVariable("id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            if (solutionService.deleteById(id) == 1) {
                basicResponse.set(200, "删除成功");
            } else {
                basicResponse.set(400, "删除失败");
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 添加提交
     *
     * @param solution 要添加的提交对象
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BasicResponse insertSolution(@RequestBody Solution solution) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            if (solutionService.insert(solution) == 1) {
                basicResponse.set(200, "添加成功");
            } else {
                basicResponse.set(400, "添加失败");
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 通过ID更新提交
     *
     * @param id       提交ID
     * @param solution 更新的提交对象
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BasicResponse updateSolutionById(@PathVariable("id") Integer id, @RequestBody Solution solution) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            //确保更新的提交ID是URL中的ID
            solution.setId(id);
            if (solutionService.updateByIdSelective(solution) == 1) {
                basicResponse.set(200, "更新成功");
            } else {
                basicResponse.set(400, "更新失败");
            }
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }
}
