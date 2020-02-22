package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.api.CreateUserGroupRequest;
import cn.edu.jmu.system.api.CreateUserGroupResponse;
import cn.edu.jmu.system.api.DeleteUserGroupResponse;
import cn.edu.jmu.system.api.SearchUserGroupResponse;
import cn.edu.jmu.system.api.UpdateUserGroupRequest;
import cn.edu.jmu.system.api.UpdateUserGroupResponse;
import cn.edu.jmu.system.service.UserGroupService;
import org.apache.shiro.authz.annotation.Logical;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xeathen
 */

@RestController
@RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
@RequestMapping("/api/admin")
public class UserGroupController {
    @Resource
    UserGroupService userGroupService;

    @GetMapping(value = "/user-group")
    public ResponseEntity<BasicResponse> search(@RequestParam Integer skip, @RequestParam Integer limit) {
        SearchUserGroupResponse response = userGroupService.search(skip, limit);
        return ResponseUtil.buildResponse(response);
    }

    @PostMapping(value = "/user-group")
    public ResponseEntity<BasicResponse> create(@RequestBody @Validated CreateUserGroupRequest request) {
        CreateUserGroupResponse response = userGroupService.create(request);
        return ResponseUtil.buildResponse(response);
    }

    @PutMapping(value = "/user-group/{id}")
    public ResponseEntity<BasicResponse> update(@PathVariable Integer id, @RequestBody UpdateUserGroupRequest request) {
        UpdateUserGroupResponse response = userGroupService.update(id, request);
        return ResponseUtil.buildResponse(response);
    }

    @DeleteMapping(value = "/user-group/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id) {
        DeleteUserGroupResponse response = userGroupService.delete(id);
        return ResponseUtil.buildResponse(response);
    }
}
