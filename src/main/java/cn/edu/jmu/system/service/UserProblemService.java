package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.UserProblem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xeathen
 * @date 2019/10/5 10:35
 */
public interface UserProblemService extends IService<UserProblem> {

    /**
     * 根据uid与pid查找记录
     * @param uid
     * @param pid
     * @return
     */
    Integer find(Integer uid, Integer pid);

    /**
     * 根据uid与pid及state差找
     * @param uid
     * @param pid
     * @param state
     * @return
     */
    Integer find(Integer uid, Integer pid, String state);



}
