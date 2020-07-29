package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.controller.handler.DeleteInBulkHandler;
import cn.edu.jmu.system.entity.ProblemCategoryPermission;
import cn.edu.jmu.system.entity.UserGroup;
import cn.edu.jmu.system.service.ProblemCategoryPermissionService;
import cn.edu.jmu.system.service.ProblemCategoryService;
import cn.edu.jmu.system.service.UserGroupService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiresPermissions(value = {"admin"})
@RequestMapping("/api/admin/problem_category_perm")
public class ProblemCategoryPermissionController {
    @Resource
    private ProblemCategoryPermissionService problemCategoryPermissionService;

    @Resource
    private UserGroupService userGroupService;

    @Resource
    private ProblemCategoryService problemCategoryService;

    @GetMapping("/user_group/{problemCategoryId}")
    public ResponseEntity<BasicResponse> getUserGroupListByProblemCategoryId(@PathVariable("problemCategoryId") Integer problemCategoryId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (problemCategoryService.existById(problemCategoryId)) {
            Page<UserGroup> userGroupPage = new Page<>(pageNum, pageSize);
            return ResponseUtil.buildResponse("查询成功", problemCategoryPermissionService.getProblemCategoryPermissionListByProblemCategoryId(userGroupPage, problemCategoryId));
        } else {
            return ResponseUtil.fail("无此题目集");
        }
    }

    @GetMapping("/user_group_ids/{problemCategoryId}")
    public ResponseEntity<BasicResponse> getUserGroupIdsByProblemCategoryId(@PathVariable("problemCategoryId") Integer problemCategoryId) {
        if (problemCategoryService.existById(problemCategoryId)) {
            return ResponseUtil.buildResponse("查询成功", problemCategoryPermissionService.getUserGroupIdsByProblemCategoryId(problemCategoryId));
        } else {
            return ResponseUtil.fail("无此题目集");
        }
    }

    @GetMapping("/problem_category_ids/{userGroupId}")
    public ResponseEntity<BasicResponse> getProblemCategoryIdsByUserGroupId(@PathVariable("userGroupId") Integer userGroupId) {
        if (userGroupService.existById(userGroupId)) {
            return ResponseUtil.buildResponse("查询成功", problemCategoryPermissionService.getProblemCategoryIdsByUserGroupId(userGroupId));
        } else {
            return ResponseUtil.fail("无此用户组");
        }
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<BasicResponse> deleteInBulk(@RequestBody List<Integer> problemCategoryPermissionIds) {
        return DeleteInBulkHandler.deleteInBulk(problemCategoryPermissionService, problemCategoryPermissionIds);
    }

    @PostMapping("/bulk/{problemCategoryId}")
    public ResponseEntity<BasicResponse> createInBulk(@PathVariable("problemCategoryId") Integer problemCategoryId, @RequestBody List<Integer> userGroupIds) {
        if (userGroupIds.isEmpty()) {
            return ResponseUtil.fail("请指定要添加的用户组ID");
        }
        if (!problemCategoryService.existById(problemCategoryId)) {
            return ResponseUtil.fail("该题目集ID不存在");
        }
        List<Integer> successIds = new ArrayList<>();
        List<Integer> failIds = new ArrayList<>();
        List<Integer> duplicatedIds = new ArrayList<>();
        ProblemCategoryPermission problemCategoryPermission = new ProblemCategoryPermission();
        problemCategoryPermission.setProblemCategoryId(problemCategoryId);
        for (Integer userGroupId : userGroupIds) {
            if (userGroupService.existById(userGroupId)) {
                if (problemCategoryPermissionService.isExistByProblemCategoryIdAndUserGroupId(problemCategoryId, userGroupId)) {
                    duplicatedIds.add(userGroupId);
                } else {
                    problemCategoryPermission.setUserGroupId(userGroupId);
                    if (problemCategoryPermissionService.save(problemCategoryPermission)) {
                        successIds.add(userGroupId);
                    } else {
                        failIds.add(userGroupId);
                    }
                }
            } else {
                failIds.add(userGroupId);
            }
        }
        HashMap<String, List<Integer>> responseHashMap = new HashMap<>(3);
        responseHashMap.put("success", successIds);
        responseHashMap.put("duplicated", duplicatedIds);
        responseHashMap.put("fail", failIds);
        return ResponseUtil.buildResponse("添加完成", responseHashMap);
    }
}
