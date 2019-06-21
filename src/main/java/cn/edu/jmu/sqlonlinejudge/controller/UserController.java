package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.sqlonlinejudge.model.User;
import cn.edu.jmu.sqlonlinejudge.model.enums.UserRole;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
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

    private UserRole userRole;

    /**
     * 查询所有用户
     *
     * @param pageNum 页码
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
     * 模糊查询用户
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return cn.edu.jmu.sqlonlinejudge.util.BasicResponse
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public BasicResponse selectBySelective(User user,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse response = new BasicResponse();
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<User> users = userService.selectBySelective(user);
            if (users != null) {
                response.set(200, "查询成功", new PageInfo<>(users));
            } else {
                response.set(400, "无符合条件用户");
            }
        } catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

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

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public BasicResponse update(@RequestBody User user) {
        BasicResponse response = new BasicResponse();
        try {
            // 确保用户ID是URL中的ID
            if(userService.updateById(user)) {
                response.set(200, "成功修改ID为" + user.getId() + "的用户资料");
            }else {
                response.set(400, "用户资料修改失败");
            }
        }catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BasicResponse insert(@RequestBody User user) {
        BasicResponse response = new BasicResponse();
        try {
            if(userService.insert(user)) {
                response.set(200, "增加用户成功", userService.selectById(user.getId()));
            }else {
                response.set(400, "增加用户失败");
            }
        }catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BasicResponse login(@RequestBody User u) {
        BasicResponse response = new BasicResponse();
        try {
            User user = userService.verify(u.getUsername(), u.getPassword());
            if(user != null) {
                response.set(200, "用户验证成功");
                userRole = user.getRole();
            }else {
                response.set(400, "用户验证失败");
            }
        }catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }
}
