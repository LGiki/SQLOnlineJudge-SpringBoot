package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.common.enums.ResponseStatusEnum;
import cn.edu.jmu.common.util.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author sgh
 * @date 2019/6/18 19:17
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询所有用户
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     */
    @GetMapping(value = "/")
    @RequiresRoles(value = {"admin"})
    public ResponseEntity<BasicResponse> getAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        Page<User> userPage = new Page<>(pageNum, pageSize);
        IPage<User> iPage = userService.page(userPage, null);
        basicResponse.wrapper(ResponseStatusEnum.OK, "查询成功", iPage);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 通过ID查询用户详情
     *
     * @param id 用户ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity selectUserById(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        User user = userService.getById(id);
        return ResponseEntity.ok().body(user);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param id 用户ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        // 删除用户
        userService.removeById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 更新用户
     *
     * @param user 新的用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody User user, @PathVariable(value = "id") Integer id) {
        // 更新用户信息
        userService.saveOrUpdate(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 添加用户
     *
     * @param user 新的用户
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity insert(@RequestBody User user) {
        // 创建用户
        userService.saveOrUpdate(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
