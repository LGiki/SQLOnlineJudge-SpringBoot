package cn.edu.jmu.system.controller.admin;


import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.system.entity.Role;
import cn.edu.jmu.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sgh
 * @since 2019-08-21
 */
@RestController
@RequestMapping("/api/admin/roles")
@RequiresRoles(value = {"admin"})
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping(value = "")
    public ResponseEntity<BasicResponse> get() {
        BasicResponse response = new BasicResponse();
        List<Role> roles = roleService.list();
        response.wrapper(AbstractResponseCode.OK, "查询角色成功", roles);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@RequestBody Role role, @PathVariable(value = "id") Integer id) {
        BasicResponse response = new BasicResponse();
        if (role != null && role.getId() != null && role.getId().equals(id)) {
            if (roleService.saveOrUpdate(role)) {
                response.wrapper(AbstractResponseCode.OK, "更新角色成功");
            } else {
                response.wrapper(AbstractResponseCode.OK, "更新角色失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable(value = "id") Integer id) {
        BasicResponse response = new BasicResponse();
        if (id != null) {
            if (roleService.removeById(id)) {
                response.wrapper(AbstractResponseCode.OK, "删除角色成功");
            } else {
                response.wrapper(AbstractResponseCode.OK, "删除角色失败");
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<BasicResponse> insert(@RequestBody @Validated Role role) {
        BasicResponse response = new BasicResponse();
        if (role != null && role.getId() == null) {
            if (roleService.saveOrUpdate(role)) {
                response.wrapper(AbstractResponseCode.OK, "增加角色成功");
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "增加角色失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
