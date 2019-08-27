package cn.edu.jmu.sqlonlinejudge.controller.admin;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.entity.dto.UserDto;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import cn.edu.jmu.sqlonlinejudge.service.mapper.UserMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author sgh
 * @date 2019/6/18 19:17
 */
@RestController
@RequiresRoles(value = {"admin"})
@RequestMapping("/api/admin/users")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询所有用户
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getAll(UserDto userDto,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<UserDto> iPage = userService.getAll(userDto, page);
        basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", iPage);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 通过ID查询用户详情
     *
     * @param id 用户ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> selectUserById(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        User user = userService.getById(id);
        UserDto userDto = UserMapper.toDto(user);
        response.wrapper(AbstractResponseCode.OK, "查询成功", userDto);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param id 用户ID
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        if (id != null) {
            // 删除用户
            if (userService.removeById(id)) {
                response.wrapper(AbstractResponseCode.OK, "删除成功");
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "删除失败");
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            response.wrapper(AbstractResponseCode.FAIL, "删除失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 更新用户
     *
     * @param userDto 新的用户信息
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@RequestBody UserDto userDto, @PathVariable(value = "id") Integer id) {
        BasicResponse response = new BasicResponse();
        if (userDto != null && userDto.getId() != null && userDto.getId().equals(id)) {
            // 更新用户信息
            if (userService.update(userDto)) {
                response.wrapper(AbstractResponseCode.OK, "更新用户信息成功", userDto);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "更新用户信息失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 添加用户
     *
     * @param userDto 新的用户
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insert(@RequestBody @Validated UserDto userDto) {
        BasicResponse response = new BasicResponse();
        if (userDto != null && userDto.getId() == null) {
            User user = UserMapper.toEntity(userDto);
            String salt = EncryptUtil.generatorSalt();
            user.setSalt(salt);
            user.setPassword(EncryptUtil.encryption(user.getUsername(), user.getPassword(), salt));
            if (userService.saveOrUpdate(user)) {
                response.wrapper(AbstractResponseCode.OK, "新增用户成功", user);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "新增用户失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 获取用户数量
     */
    @GetMapping(value = "/count")
    public ResponseEntity<BasicResponse> count() {
        BasicResponse response = new BasicResponse();
        response.wrapper(AbstractResponseCode.OK, "获取数量成功", userService.count());
        return ResponseEntity.ok().body(response);
    }
}
