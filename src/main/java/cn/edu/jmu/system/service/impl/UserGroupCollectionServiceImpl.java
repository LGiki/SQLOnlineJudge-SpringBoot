package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.UserGroupCollection;
import cn.edu.jmu.system.mapper.UserGroupCollectionMapper;
import cn.edu.jmu.system.service.UserGroupCollectionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserGroupCollectionServiceImpl extends ServiceImpl<UserGroupCollectionMapper, UserGroupCollection> implements UserGroupCollectionService {

    @Override
    public IPage<UserGroupCollection> getUserGroupCollectionList(UserGroupCollection userGroupCollection, Page page) {
        Page<UserGroupCollection> userGroupCollectionPage = new Page<>(page.getCurrent(), page.getSize());
        return baseMapper.selectPage(userGroupCollectionPage, predicate(userGroupCollection));
    }

    @Override
    public Boolean existById(Integer userGroupCollectionId) {
        return baseMapper.selectCount(Wrappers.<UserGroupCollection>lambdaQuery().eq(UserGroupCollection::getId, userGroupCollectionId)) != 0;
    }

    private LambdaQueryWrapper<UserGroupCollection> predicate(UserGroupCollection userGroupCollection) {
        if (userGroupCollection == null) {
            return null;
        } else {
            LambdaQueryWrapper<UserGroupCollection> queryWrapper = new LambdaQueryWrapper<>();
            if (userGroupCollection.getUserGroupId() != null) {
                queryWrapper.eq(UserGroupCollection::getUserGroupId, userGroupCollection.getUserGroupId());
            } else if (userGroupCollection.getUserId() != null) {
                queryWrapper.eq(UserGroupCollection::getUserId, userGroupCollection.getUserId());
            }
            return queryWrapper;
        }
    }

    @Override
    public Boolean isUserInUserGroupCollection(Integer userId, Integer userGroupId) {
        return baseMapper.selectCount(Wrappers.<UserGroupCollection>lambdaQuery().eq(UserGroupCollection::getUserId, userId).eq(UserGroupCollection::getUserGroupId, userGroupId)) > 0;
    }
}
