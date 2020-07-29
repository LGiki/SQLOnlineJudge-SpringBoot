package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.UserGroupCollection;
import cn.edu.jmu.system.entity.dto.UserGroupCollectionDto;
import cn.edu.jmu.system.mapper.UserGroupCollectionMapper;
import cn.edu.jmu.system.service.UserGroupCollectionService;
import cn.edu.jmu.system.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserGroupCollectionServiceImpl extends ServiceImpl<UserGroupCollectionMapper, UserGroupCollection> implements UserGroupCollectionService {

    @Resource
    private UserService userService;

    @Override
    public IPage<UserGroupCollectionDto> getUserGroupCollectionList(UserGroupCollection userGroupCollection, Page page) {
        Page<UserGroupCollection> userGroupCollectionPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<UserGroupCollection> userGroupCollectionIPage = baseMapper.selectPage(userGroupCollectionPage, predicate(userGroupCollection));
        return userGroupCollectionIPage.convert(this::convertUserGroupCollectionToDto);
    }

    private UserGroupCollectionDto convertUserGroupCollectionToDto(UserGroupCollection userGroupCollection) {
        UserGroupCollectionDto userGroupCollectionDto = new UserGroupCollectionDto();
        BeanUtil.copyProperties(userGroupCollection, userGroupCollectionDto);
        User user = userService.getById(userGroupCollection.getUserId());
        if (user != null) {
            userGroupCollectionDto.setStudentNo(user.getStudentNo());
            userGroupCollectionDto.setUsername(user.getUsername());
        }
        return userGroupCollectionDto;
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
    public Boolean isExistByUserIdAndUserGroupId(Integer userId, Integer userGroupId) {
        return baseMapper.selectCount(Wrappers.<UserGroupCollection>lambdaQuery().eq(UserGroupCollection::getUserId, userId).eq(UserGroupCollection::getUserGroupId, userGroupId)) > 0;
    }

    @Override
    public Integer countByUserGroupId(Integer userGroupId) {
        return baseMapper.selectCount(Wrappers.<UserGroupCollection>lambdaQuery().eq(UserGroupCollection::getUserGroupId, userGroupId));
    }

    @Override
    public List<Integer> getUserIdsByUserGroupId(Integer userGroupId) {
        return baseMapper.selectList(Wrappers.<UserGroupCollection>lambdaQuery().eq(UserGroupCollection::getUserGroupId, userGroupId)).stream().map(UserGroupCollection::getUserId).collect(Collectors.toList());
    }
}
