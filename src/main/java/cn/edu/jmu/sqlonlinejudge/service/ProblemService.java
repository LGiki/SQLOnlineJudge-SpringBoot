package cn.edu.jmu.sqlonlinejudge.service;

import cn.edu.jmu.sqlonlinejudge.model.Problem;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

public interface ProblemService {
    /**
     * 通过ID删除题目
     *
     * @param id 题目ID
     * @return 删除成功的记录条数
     */
    int deleteById(Integer id);

    /**
     * 添加题目
     *
     * @param record 题目对象
     * @return 添加成功的记录条数
     */
    int insert(Problem record);

    /**
     * 选择性添加题目
     *
     * @param record 题目对象
     * @return 添加成功的记录条数
     */
    int insertSelective(Problem record);

    /**
     * 通过ID获取题目信息
     *
     * @param id 题目ID
     * @return 题目对象
     */
    Problem selectById(Integer id);

    /**
     * 选择性通过ID更新题目
     *
     * @param record 题目对象
     * @return 更新成功的记录条数
     */
    int updateByIdSelective(Problem record);

    /**
     * 通过ID更新题目
     *
     * @param record 题目对象
     * @return 更新成功的记录条数
     */
    int updateById(Problem record);

}
