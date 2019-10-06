package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.UserProblem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xeathen
 * @date 2019/10/5 10:35
 */
public interface UserProblemService extends IService<UserProblem> {

    /**
     *
     * @param uid
     * @param pid
     * @return
     */
    boolean isExist(Integer uid, Integer pid);

//    boolean update(UserProblem userProblem);
}
