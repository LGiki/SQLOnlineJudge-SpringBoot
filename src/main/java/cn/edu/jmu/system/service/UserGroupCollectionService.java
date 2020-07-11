package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.UserGroupCollection;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserGroupCollectionService extends IService<UserGroupCollection> {
    /**
     * 查询用户组关系列表
     *
     * @param userGroupCollection 用户组关系查询对象
     * @param page                分页信息
     * @return IPage<UserGroupDto>
     */
    IPage<UserGroupCollection> getUserGroupCollectionList(UserGroupCollection userGroupCollection, Page page);

    /**
     * 根据ID判断用户组关系是否存在
     *
     * @param userGroupCollectionId 用户组ID
     * @return Boolean 用户组关系是否存在
     */
    Boolean existById(Integer userGroupCollectionId);

    /**
     * 判断userId对应的用户是否已经在userGroupId对应的用户组关系里了
     *
     * @param userId      用户ID
     * @param userGroupId 用户组ID
     * @return Boolean 用户是否已在用户组关系中
     */
    Boolean isUserInUserGroupCollection(Integer userId, Integer userGroupId);
}
