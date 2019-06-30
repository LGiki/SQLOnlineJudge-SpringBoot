package cn.edu.jmu.sqlonlinejudge.mapper;

import org.apache.ibatis.annotations.Param;

import cn.edu.jmu.sqlonlinejudge.model.Problem;

import java.util.List;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

public interface ProblemMapper {
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
     * 通过ID获取题目对象
     *
     * @param id 题目ID
     * @return 题目对象
     */
    Problem selectById(Integer id);

    /**
     * 通过ID选择性更新题目
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

    /**
     * 查询所有题目
     *
     * @return List<Problem> 题目列表
     */
    List<Problem> selectAll();

    /**
     * 模糊查找题目
     *
     * @param keyword 关键字
     * @return List<Problem> 题目列表
     */
    List<Problem> selectAllByKeyword(@Param("keyword") String keyword);

    /**
     * 查询题目数量
     *
     * @return Integer 题目数量
     */
    Integer countAll();


}