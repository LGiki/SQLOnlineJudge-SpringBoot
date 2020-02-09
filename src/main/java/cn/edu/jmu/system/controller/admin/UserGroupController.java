package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.api.CreateUserGroupRequest;
import cn.edu.jmu.system.api.CreateUserGroupResponse;
import cn.edu.jmu.system.service.UserGroupService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping(value = "/user-group")
    public ResponseEntity<BasicResponse> create(@RequestBody @Validated CreateUserGroupRequest request) {
        CreateUserGroupResponse response = userGroupService.create(request);
        return ResponseUtil.buildResponse(response);
    }
}
