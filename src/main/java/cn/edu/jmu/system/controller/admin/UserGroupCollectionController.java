package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.controller.handler.DeleteInBulkHandler;
import cn.edu.jmu.system.entity.UserGroupCollection;
import cn.edu.jmu.system.entity.dto.UserGroupCollectionDto;
import cn.edu.jmu.system.service.UserGroupCollectionService;
import cn.edu.jmu.system.service.UserGroupService;
import cn.edu.jmu.system.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
@RequestMapping("/api/admin/user_group_collection")
@Slf4j
public class UserGroupCollectionController {
    @Resource
    private UserGroupCollectionService userGroupCollectionService;

    @Resource
    private UserGroupService userGroupService;

    @Resource
    private UserService userService;

    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getList(UserGroupCollection userGroupCollection, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<UserGroupCollection> page = new Page<>(pageNum, pageSize);
        IPage<UserGroupCollectionDto> iPage = userGroupCollectionService.getUserGroupCollectionList(userGroupCollection, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    @DeleteMapping(value = "/{userGroupCollectionId}")
    public ResponseEntity<BasicResponse> deleteById(@PathVariable("userGroupCollectionId") Integer userGroupCollectionId) {
        return ResponseUtil.buildResponse(userGroupCollectionService.removeById(userGroupCollectionId), "删除用户组关系成功", "删除用户组关系失败");
    }

    @PostMapping(value = "/bulk/{userGroupId}")
    public ResponseEntity<BasicResponse> insertUserToUserGroupInBulk(@PathVariable("userGroupId") Integer userGroupId, @RequestBody List<Integer> userIds) {
        if (userIds.isEmpty()) {
            return ResponseUtil.fail("请选择要添加到用户组中的用户");
        }
        if (!userGroupService.existById(userGroupId)) {
            return ResponseUtil.fail("该用户组ID不存在");
        }
        List<Integer> successIds = new ArrayList<>();
        List<Integer> failIds = new ArrayList<>();
        List<Integer> duplicatedIds = new ArrayList<>();
        UserGroupCollection userGroupCollection = new UserGroupCollection();
        userGroupCollection.setUserGroupId(userGroupId);
        for (Integer userId : userIds) {
            if (userService.existById(userId)) {
                if (userGroupCollectionService.isExistByUserIdAndUserGroupId(userId, userGroupId)) {
                    duplicatedIds.add(userId);
                } else {
                    userGroupCollection.setUserId(userId);
                    if (userGroupCollectionService.save(userGroupCollection)) {
                        successIds.add(userId);
                    } else {
                        failIds.add(userId);
                    }
                }
            } else {
                failIds.add(userId);
            }
        }
        HashMap<String, List<Integer>> responseHashMap = new HashMap<>(3);
        responseHashMap.put("success", successIds);
        responseHashMap.put("duplicated", duplicatedIds);
        responseHashMap.put("fail", failIds);
        return ResponseUtil.buildResponse("添加用户完成", responseHashMap);
    }

    @DeleteMapping(value = "/bulk")
    public ResponseEntity<BasicResponse> deleteUserGroupCollectionInBulk(@RequestBody List<Integer> userGroupCollectionIds) {
        return DeleteInBulkHandler.deleteInBulk(userGroupCollectionService, userGroupCollectionIds);
    }

    /**
     * 通过用户组ID查找用户组所包含的所有用户的ID
     *
     * @param userGroupId 用户组ID
     */
    @GetMapping("/user_ids/{userGroupId}")
    public ResponseEntity<BasicResponse> getUserIdsByUserGroupId(@PathVariable("userGroupId") Integer userGroupId) {
        if (!userGroupService.existById(userGroupId)) {
            return ResponseUtil.fail("该用户组不存在");
        } else {
            return ResponseUtil.buildResponse("查询成功", userGroupCollectionService.getUserIdsByUserGroupId(userGroupId));
        }
    }

}
