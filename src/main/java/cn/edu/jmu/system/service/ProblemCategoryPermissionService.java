package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.ProblemCategoryPermission;
import cn.edu.jmu.system.entity.UserGroup;
import cn.edu.jmu.system.entity.dto.ProblemCategoryPermissionDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProblemCategoryPermissionService extends IService<ProblemCategoryPermission> {
    /**
     * 通过题目集ID获得对应的用户组ID列表
     *
     * @param problemCategoryId 题目集
     * @return List<Integer> 用户组ID列表
     */
    List<Integer> getUserGroupIdsByProblemCategoryId(Integer problemCategoryId);

    /**
     * 通过用户组ID获得对应的题目集ID列表
     *
     * @param userGroupId 用户组ID
     * @return List<Integer> 题目集ID列表
     */
    List<Integer> getProblemCategoryIdsByUserGroupId(Integer userGroupId);

    /**
     * 根据题目集ID和用户组ID判断ProblemCategoryPermission是否存在
     *
     * @param problemCategoryId 题目集ID
     * @param userGroupId       用户组ID
     * @return Boolean ProblemCategoryPermission是否存在
     */
    Boolean isExistByProblemCategoryIdAndUserGroupId(Integer problemCategoryId, Integer userGroupId);


    /**
     * 通过题目集ID获取用户组列表
     *
     * @param problemCategoryId 题目集ID
     * @return IPage<UserGroup>
     */
    IPage<ProblemCategoryPermissionDto> getProblemCategoryPermissionListByProblemCategoryId(Page<UserGroup> page, Integer problemCategoryId);
}
