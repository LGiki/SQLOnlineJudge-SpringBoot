package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.UserProblem;
import cn.edu.jmu.system.mapper.UserProblemMapper;
import cn.edu.jmu.system.service.UserProblemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author xeathen
 * @date 2019/10/5 10:36
 */
@Service
public class UserProblemServiceImpl extends ServiceImpl<UserProblemMapper, UserProblem> implements UserProblemService {

    /**
     * 判断记录是否存在
     * @param uid
     * @param pid
     * @return
     */
    @Override
    public boolean isExist(Integer uid, Integer pid) {
        UserProblem userProblem = new UserProblem();
        userProblem.setUid(uid);
        userProblem.setPid(pid);
        QueryWrapper<UserProblem> queryWrapper = new QueryWrapper<>(userProblem);
        UserProblem selectOne = baseMapper.selectOne(queryWrapper);
        return selectOne != null;
    }


}
