package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.edu.jmu.sqlonlinejudge.util.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sgh
 * @date 2019/6/18 19:17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public BasicResponse delete(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        try {
            if (userService.deleteById(id)) {
                response.set(200, HttpStatus.OK, "删除ID为" + id + "的用户成功");
            } else {
                response.set(417, HttpStatus.EXPECTATION_FAILED, "删除用户失败，无ID为" + id + "的用户");
            }
        } catch (Exception e) {
            response.setExceptionMessage(412, HttpStatus.PRECONDITION_FAILED, e.getMessage());
        }
        return response;
    }
}
