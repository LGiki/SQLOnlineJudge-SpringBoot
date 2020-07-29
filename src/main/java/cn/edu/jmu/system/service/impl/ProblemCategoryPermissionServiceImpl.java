package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.ProblemCategoryPermission;
import cn.edu.jmu.system.entity.UserGroup;
import cn.edu.jmu.system.entity.dto.ProblemCategoryPermissionDto;
import cn.edu.jmu.system.mapper.ProblemCategoryPermissionMapper;
import cn.edu.jmu.system.service.ProblemCategoryPermissionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemCategoryPermissionServiceImpl extends ServiceImpl<ProblemCategoryPermissionMapper, ProblemCategoryPermission> implements ProblemCategoryPermissionService {
    @Resource
    private ProblemCategoryPermissionMapper problemCategoryPermissionMapper;

    @Override
    public List<Integer> getUserGroupIdsByProblemCategoryId(Integer problemCategoryId) {
        return baseMapper.selectList(Wrappers.<ProblemCategoryPermission>lambdaQuery().eq(ProblemCategoryPermission::getProblemCategoryId, problemCategoryId)).stream().map(ProblemCategoryPermission::getUserGroupId).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getProblemCategoryIdsByUserGroupId(Integer userGroupId) {
        return baseMapper.selectList(Wrappers.<ProblemCategoryPermission>lambdaQuery().eq(ProblemCategoryPermission::getUserGroupId, userGroupId)).stream().map(ProblemCategoryPermission::getProblemCategoryId).collect(Collectors.toList());
    }

    @Override
    public Boolean isExistByProblemCategoryIdAndUserGroupId(Integer problemCategoryId, Integer userGroupId) {
        return baseMapper.selectCount(Wrappers.<ProblemCategoryPermission>lambdaQuery().eq(ProblemCategoryPermission::getProblemCategoryId, problemCategoryId).eq(ProblemCategoryPermission::getUserGroupId, userGroupId)) > 0;
    }

    @Override
    public IPage<ProblemCategoryPermissionDto> getProblemCategoryPermissionListByProblemCategoryId(Page<UserGroup> page, Integer problemCategoryId) {
        return problemCategoryPermissionMapper.selectByProblemCategoryId(page, problemCategoryId);
    }
}
