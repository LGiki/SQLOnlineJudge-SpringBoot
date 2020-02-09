package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.entity.Permission;
import cn.edu.jmu.system.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> get() {
        List<Permission> permissions = permissionService.list();
        return ResponseUtil.buildResponse("查询权限成功", permissions);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@RequestBody Permission permission, @PathVariable(value = "id") Integer id) {
        if (permission.getId() != null && permission.getId().equals(id)) {
            boolean success = permissionService.saveOrUpdate(permission);
            return ResponseUtil.buildResponse(success, "更新权限成功", "更新权限失败");
        } else {
            return ResponseUtil.fail("id不一致");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable(value = "id") Integer id) {
        boolean success = permissionService.removeById(id);
        return ResponseUtil.buildResponse(success, "删除权限成功", "删除权限失败");
    }

    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insert(@RequestBody @Validated Permission permission) {
        boolean success = permissionService.saveOrUpdate(permission);
        return ResponseUtil.buildResponse(success, "增加权限成功", "增加权限失败");
    }
}
