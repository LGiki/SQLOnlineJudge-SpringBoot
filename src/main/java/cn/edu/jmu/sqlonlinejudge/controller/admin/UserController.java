package cn.edu.jmu.sqlonlinejudge.controller.admin;

import cn.edu.jmu.sqlonlinejudge.model.User;
import cn.edu.jmu.sqlonlinejudge.service.enumerate.UserRoleEnum;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.util.EncryptUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author sgh
 * @date 2019/6/18 19:17
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private UserRoleEnum userRole;

    /**
     * 查询所有用户
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BasicResponse getAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse response = new BasicResponse();
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<User> users = userService.selectAll();
            if (users != null) {
                response.set(200, "查询成功", new PageInfo<>(users));
            } else {
                response.set(400, "无用户");
            }
        } catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    /**
     * 通过ID查询用户详情
     *
     * @param id 用户ID
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BasicResponse selectUserById(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        try {
            User users = userService.selectById(id);
            if (users != null) {
                response.set(200, null, users);
            } else {
                response.set(400, "无此用户");
            }
        } catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    /**
     * 模糊查询用户
     *
     * @param keyword  关键字
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public BasicResponse selectBySelective(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse response = new BasicResponse();
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<User> users = userService.selectAllByKeyword(keyword);
            if (users != null) {
                response.set(200, null, new PageInfo<>(users));
            } else {
                response.set(400, "无符合条件的用户");
            }
        } catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    /**
     * 通过用户ID删除用户
     *
     * @param id 用户ID
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BasicResponse delete(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        try {
            if (userService.deleteById(id)) {
                response.set(200, "删除ID为" + id + "的用户成功");
            } else {
                response.set(400, "删除用户失败，无ID为" + id + "的用户");
            }
        } catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    /**
     * 更新用户
     *
     * @param user 新的用户信息
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BasicResponse update(@RequestBody User user, @PathVariable(value = "id") Integer id) {
        BasicResponse response = new BasicResponse();
        try {
            // 确保用户ID是URL中的ID
            user.setId(id);
            user.setPassword(EncryptUtil.md5(user.getPassword()));
            if (userService.updateById(user)) {
                response.set(200, "成功修改ID为" + user.getId() + "的用户资料");
            } else {
                response.set(400, "用户资料修改失败");
            }
        } catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    /**
     * 添加用户
     *
     * @param user 新的用户
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BasicResponse insert(@RequestBody User user) {
        BasicResponse response = new BasicResponse();
        //MD5 加密用户密码
        user.setPassword(EncryptUtil.md5(user.getPassword()));
        try {
            if (userService.insert(user)) {
                response.set(200, "增加用户成功", userService.selectById(user.getId()));
            } else {
                response.set(400, "增加用户失败");
            }
        } catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    /**
     * 用户登录
     *
     * @param user 登录的用户
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BasicResponse login(@RequestBody User user) {
        BasicResponse response = new BasicResponse();
        try {
            User verifyResult = userService.verify(user.getUsername(), user.getPassword());
            if (verifyResult != null) {
                response.set(200, "用户验证成功");
                userRole = verifyResult.getRole();
            } else {
                response.set(400, "用户验证失败");
            }
        } catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    /**
     * 查询所有用户并按通过数降序排序
     *
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/ranklist", method = RequestMethod.GET)
    public BasicResponse getRankList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        try {
            PageHelper.startPage(pageNum, pageSize);
            basicResponse.set(200, null, new PageInfo<>(userService.selectAllOrderBySolvedDesc()));
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }

    /**
     * 查询用户数量
     *
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BasicResponse countAll() {
        BasicResponse basicResponse = new BasicResponse();
        try {
            basicResponse.set(200, null, userService.countAll());
        } catch (Exception e) {
            basicResponse.set(503, e.getCause().toString());
        }
        return basicResponse;
    }
}
