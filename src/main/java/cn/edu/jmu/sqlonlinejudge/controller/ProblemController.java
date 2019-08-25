package cn.edu.jmu.sqlonlinejudge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
//
//    @Autowired
//    private ProblemService problemService;
//
//    /**
//     * 查询所有题目
//     *
//     * @param pageNum  当前页码
//     * @param pageSize 页面数据条数
//     * @return cn.edu.jmu.common.response.BasicResponse
//     */
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public BasicResponse selectAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
//        BasicResponse basicResponse = new BasicResponse();
//        try {
////            Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//////            if(object instanceof User) {
//////                User user = (User) object;
//////                System.out.println(user.getUsername());
//////                System.out.println(user.getRole().getDisplayName());
//////            }
//            PageHelper.startPage(pageNum, pageSize);
//            basicResponse.wrapper(200, null, new PageInfo<>(problemService.selectAll()));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            basicResponse.wrapper(503, e.getCause().toString());
//        }
//        return basicResponse;
//    }
//
//    /**
//     * 通过ID查询题目详情
//     *
//     * @param id 题目ID
//     * @return cn.edu.jmu.common.response.BasicResponse
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public BasicResponse selectProblemById(@PathVariable("id") Integer id) {
//        BasicResponse basicResponse = new BasicResponse();
//        try {
//            Problem problem = problemService.selectById(id);
//            if (problem != null) {
//                basicResponse.wrapper(200, null, problem);
//            } else {
//                basicResponse.wrapper(400, "无此题目", null);
//            }
//        } catch (Exception e) {
//            basicResponse.wrapper(503, e.getCause().toString());
//        }
//        return basicResponse;
//    }
//
//    /**
//     * 通过ID删除题目
//     *
//     * @param id 题目ID
//     * @return cn.edu.jmu.common.response.BasicResponse
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public BasicResponse deleteProblemById(@PathVariable("id") Integer id) {
//        BasicResponse basicResponse = new BasicResponse();
//        try {
//            if (problemService.deleteById(id) == 1) {
//                basicResponse.wrapper(200, "题目删除成功");
//            } else {
//                basicResponse.wrapper(400, "题目删除失败");
//            }
//        } catch (Exception e) {
//            basicResponse.wrapper(503, e.getCause().toString());
//        }
//        return basicResponse;
//    }
//
//    /**
//     * 添加题目
//     *
//     * @param problem 要添加的题目
//     * @return cn.edu.jmu.common.response.BasicResponse
//     */
//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public BasicResponse insertProblem(@RequestBody Problem problem) {
//        BasicResponse basicResponse = new BasicResponse();
//        problem.setSolve(0);
//        problem.setSubmit(0);
//        try {
//            if (problemService.insert(problem) == 1) {
//                basicResponse.wrapper(200, "题目添加成功");
//            } else {
//                basicResponse.wrapper(400, "题目添加失败");
//            }
//        } catch (Exception e) {
//            basicResponse.wrapper(503, e.getCause().toString());
//        }
//        return basicResponse;
//    }
//
//    /**
//     * 通过ID更新题目
//     *
//     * @param id      题目ID
//     * @param problem 更新的题目对象
//     * @return cn.edu.jmu.common.response.BasicResponse
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public BasicResponse updateProblemById(@PathVariable("id") Integer id, @RequestBody Problem problem) {
//        BasicResponse basicResponse = new BasicResponse();
//        try {
//            //确保更新的题目ID是URL中的ID
//            problem.setId(id);
//            if (problemService.updateByIdSelective(problem) == 1) {
//                basicResponse.wrapper(200, "题目更新成功");
//            } else {
//                basicResponse.wrapper(400, "题目更新失败");
//            }
//        } catch (Exception e) {
//            basicResponse.wrapper(503, e.getCause().toString());
//        }
//        return basicResponse;
//    }
//
//    /**
//     * 模糊查询题目
//     *
//     * @param keyword  关键字
//     * @param pageNum  页码
//     * @param pageSize 每页大小
//     * @return cn.edu.jmu.common.response.BasicResponse
//     */
//    @RequestMapping(value = "/search", method = RequestMethod.GET)
//    public BasicResponse selectByKeyword(@RequestParam(value = "keyword", defaultValue = "") String keyword,
//                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
//                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
//        BasicResponse basicResponse = new BasicResponse();
//        try {
//            PageHelper.startPage(pageNum, pageSize);
//            List<Problem> problems = problemService.selectAllByKeyword(keyword);
//            if (problems != null) {
//                basicResponse.wrapper(200, null, new PageInfo<>(problems));
//            } else {
//                basicResponse.wrapper(400, "无符合条件的题目");
//            }
//        } catch (Exception e) {
//            basicResponse.wrapper(503, e.getCause().toString());
//        }
//        return basicResponse;
//    }
//
//
//    /**
//     * 查询题目数量
//     *
//     * @return cn.edu.jmu.common.response.BasicResponse
//     */
//    @RequestMapping(value = "/count", method = RequestMethod.GET)
//    public BasicResponse countAll() {
//        BasicResponse basicResponse = new BasicResponse();
//        try {
//            basicResponse.wrapper(200, null, problemService.countAll());
//        } catch (Exception e) {
//            basicResponse.wrapper(503, e.getCause().toString());
//        }
//        return basicResponse;
//    }
}
