package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.UserProblem;
import cn.edu.jmu.system.mapper.UserProblemMapper;
import cn.edu.jmu.system.service.UserProblemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xeathen
 * @date 2019/10/5 10:36
 */
@Service
public class UserProblemServiceImpl extends ServiceImpl<UserProblemMapper, UserProblem> implements UserProblemService {

    /**
     * @param userId    用户ID
     * @param problemId 题目ID
     */
    @Override
    public Integer find(Integer userId, Integer problemId) {
        UserProblem userProblem = new UserProblem();
        userProblem.setUid(userId);
        userProblem.setPid(problemId);
        UserProblem selectOne = query(userProblem);
        if (selectOne == null) {
            return 0;
        } else {
            return selectOne.getId();
        }
    }

    /**
     * @param userId 用户ID
     * @param problemId 题目ID
     * @param passed 是否通过
     */
    @Override
    public Integer find(Integer userId, Integer problemId, Boolean passed) {
        UserProblem userProblem = new UserProblem();
        userProblem.setUid(userId);
        userProblem.setPid(problemId);
        userProblem.setPassed(passed);
        UserProblem selectOne = query(userProblem);
        if (selectOne == null) {
            return 0;
        } else {
            return selectOne.getId();
        }
    }

    private UserProblem query(UserProblem userProblem) {
        QueryWrapper<UserProblem> queryWrapper = new QueryWrapper<>(userProblem);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 通过用户ID、题目集ID、通过状态查找题目ID集合
     *
     * @param userId            用户ID
     * @param problemCategoryId 题目集ID
     * @param passed            是否通过
     * @return List<Integer> 题目ID列表
     */
    @Override
    public List<Integer> findByUserIdAndProblemCategoryIdAndPassed(Integer userId, Integer problemCategoryId, Boolean passed) {
        return baseMapper.selectList(Wrappers.<UserProblem>lambdaQuery().eq(UserProblem::getUid, userId).eq(UserProblem::getProblemCategoryId, problemCategoryId).eq(UserProblem::getPassed, passed)).stream().map(UserProblem::getPid).collect(Collectors.toList());
    }
}
