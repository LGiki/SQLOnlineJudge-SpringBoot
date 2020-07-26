package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.api.usergourp.CreateUserGroupRequest;
import cn.edu.jmu.system.api.usergourp.CreateUserGroupResponse;
import cn.edu.jmu.system.api.usergourp.DeleteUserGroupResponse;
import cn.edu.jmu.system.api.usergourp.UpdateUserGroupRequest;
import cn.edu.jmu.system.api.usergourp.UpdateUserGroupResponse;
import cn.edu.jmu.system.entity.UserGroup;
import cn.edu.jmu.system.entity.dto.UserGroupDto;
import cn.edu.jmu.system.service.UserGroupService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RequestMapping("/api/admin/user_group")
public class UserGroupController {
    @Resource
    UserGroupService userGroupService;

    @GetMapping("/count")
    public ResponseEntity<BasicResponse> count() {
        return ResponseUtil.buildResponse(userGroupService.count());
    }

    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getUserGroupList(@Validated UserGroupDto userGroupDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<UserGroup> page = new Page<>(pageNum, pageSize);
        IPage<UserGroupDto> iPage = userGroupService.search(userGroupDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getOneById(@PathVariable("id") Integer userGroupId) {
        UserGroup userGroup = userGroupService.getById(userGroupId);
        if (userGroup != null) {
            return ResponseUtil.buildResponse("查询成功", userGroup);
        } else {
            return ResponseUtil.fail("无法找到该ID对应的用户组");
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> create(@RequestBody @Validated CreateUserGroupRequest request) {
        CreateUserGroupResponse response = userGroupService.create(request);
        return ResponseUtil.buildResponse("新增成功", response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@PathVariable Integer id, @RequestBody UpdateUserGroupRequest request) {
        UpdateUserGroupResponse response = userGroupService.update(id, request);
        return ResponseUtil.buildResponse("更新成功", response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id) {
        DeleteUserGroupResponse response = userGroupService.delete(id);
        return ResponseUtil.buildResponse("删除成功", response);
    }
}
