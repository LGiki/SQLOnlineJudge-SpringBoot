package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.sqlonlinejudge.model.User;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author sgh
 * @date 2019/6/18 19:17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public BasicResponse selectAll(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        BasicResponse response = new BasicResponse();
        try {
            PageHelper.startPage(page, limit);
            response.set(200, null, new PageInfo<>(userService.selectAll()));
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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BasicResponse update(@PathVariable("id") Integer id, @RequestBody User user) {
        BasicResponse response = new BasicResponse();
        try {
            // 确保用户ID是URL中的ID
            user.setId(id);
            if(userService.updateByIdSelective(user)) {
                response.set(200, "成功修改ID为" + id + "的用户资料");
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
        //TODO: 优化错误信息
        BasicResponse response = new BasicResponse();
        try {
            if(userService.insertSelective(user)) {
                response.set(200, "增加用户成功", userService.selectById(user.getId()));
            }else {
                response.set(400, "增加用户失败");
            }
        }catch (Exception e) {
            response.set(503, e.getCause().toString());
        }
        return response;
    }
}
