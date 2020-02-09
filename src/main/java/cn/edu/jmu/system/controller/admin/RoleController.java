package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.entity.Role;
import cn.edu.jmu.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
@RequestMapping("/api/admin/roles")
@RequiresRoles(value = {"admin"})
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> get() {
        List<Role> roles = roleService.list();
        return ResponseUtil.buildResponse("查询成功", roles);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@RequestBody @Validated Role role, @PathVariable(value = "id") Integer id) {
        if (role.getId() != null && role.getId().equals(id)) {
            boolean success = roleService.saveOrUpdate(role);
            return ResponseUtil.buildResponse(success, "更新角色成功", "更新角色失败");
        } else {
            return ResponseUtil.fail("id不一致");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable(value = "id") Integer id) {
        boolean success = roleService.removeById(id);
        return ResponseUtil.buildResponse(success, "删除角色成功", "删除角色失败");
    }

    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insert(@RequestBody @Validated Role role) {
        boolean success = roleService.saveOrUpdate(role);
        return ResponseUtil.buildResponse(success, "增加角色成功", "增加角色失败");
    }
}
