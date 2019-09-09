package cn.edu.jmu.system.controller.admin;


import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.system.entity.Permission;
import cn.edu.jmu.system.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequiresPermissions(value = {"admin"})
@RequestMapping("/api/admin/permissions")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @GetMapping(value = "")
    public ResponseEntity<BasicResponse> get() {
        BasicResponse response = new BasicResponse();
        List<Permission> permissions = permissionService.list();
        response.wrapper(AbstractResponseCode.OK, "查询权限成功", permissions);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@RequestBody Permission permission, @PathVariable(value = "id") Integer id) {
        BasicResponse response = new BasicResponse();
        if (permission != null && permission.getId() != null && permission.getId().equals(id)) {
            if (permissionService.saveOrUpdate(permission)) {
                response.wrapper(AbstractResponseCode.OK, "更新权限成功");
            } else {
                response.wrapper(AbstractResponseCode.OK, "更新权限失败");
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
            if (permissionService.removeById(id)) {
                response.wrapper(AbstractResponseCode.OK, "删除权限成功");
            } else {
                response.wrapper(AbstractResponseCode.OK, "删除权限失败");
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<BasicResponse> insert(@RequestBody @Validated Permission permission) {
        BasicResponse response = new BasicResponse();
        if (permission != null && permission.getId() == null) {
            if (permissionService.saveOrUpdate(permission)) {
                response.wrapper(AbstractResponseCode.OK, "增加权限成功");
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "增加权限失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
