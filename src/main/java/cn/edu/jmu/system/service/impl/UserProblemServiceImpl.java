package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.UserProblem;
import cn.edu.jmu.system.mapper.UserProblemMapper;
import cn.edu.jmu.system.service.UserProblemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xeathen
 * @date 2019/10/5 10:36
 */
@Service
public class UserProblemServiceImpl extends ServiceImpl<UserProblemMapper, UserProblem> implements UserProblemService {

    /**
     * @param uid
     * @param pid
     * @return
     */
    @Override
    public Integer find(Integer uid, Integer pid) {
        UserProblem userProblem = new UserProblem();
        userProblem.setUid(uid);
        userProblem.setPid(pid);
        UserProblem selectOne = query(userProblem);
        if (selectOne == null) {
            return 0;
        } else {
            return selectOne.getId();
        }
    }

    /**
     * @param uid
     * @param pid
     * @param state
     * @return
     */
    @Override
    public Integer find(Integer uid, Integer pid, Boolean state) {
        UserProblem userProblem = new UserProblem();
        userProblem.setUid(uid);
        userProblem.setPid(pid);
        userProblem.setState(state);
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

    @Override
    public List<Integer> findByUidAndState(Integer uid, Boolean state) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("uid", uid);
        columnMap.put("state", state);
        return baseMapper.selectByMap(columnMap).stream().map(UserProblem::getPid).collect(Collectors.toList());
    }
}
