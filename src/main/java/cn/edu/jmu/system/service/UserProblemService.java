package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.UserProblem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author xeathen
 * @date 2019/10/5 10:35
 */
public interface UserProblemService extends IService<UserProblem> {

    /**
     * 根据用户ID和题目ID查找UserProblem记录
     *
     * @param userId    用户id
     * @param problemId 题目id
     * @return Integer
     */
    Integer find(Integer userId, Integer problemId);

    /**
     * 根据用户ID、题目ID和是否通过查找UserProblem记录
     *
     * @param userId    用户ID
     * @param problemId 题目ID
     * @param passed    是否通过
     * @return Integer
     */
    Integer find(Integer userId, Integer problemId, Boolean passed);

    /**
     * 通过用户ID、题目集ID、通过状态查找题目ID集合
     *
     * @param userId            用户ID
     * @param problemCategoryId 题目集ID
     * @param passed            是否通过
     * @return List<Integer> 题目ID集合
     */
    List<Integer> findByUserIdAndProblemCategoryIdAndPassed(Integer userId, Integer problemCategoryId, Boolean passed);
}
