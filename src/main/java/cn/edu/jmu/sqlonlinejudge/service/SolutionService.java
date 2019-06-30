package cn.edu.jmu.sqlonlinejudge.service;

import java.util.List;

import cn.edu.jmu.sqlonlinejudge.model.Solution;
import cn.edu.jmu.sqlonlinejudge.model.SolutionDetail;


/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */
public interface SolutionService {

    /**
     * 通过ID删除提交
     *
     * @param id 提交ID
     * @return 删除成功的记录条数
     */
    int deleteById(Integer id);

    /**
     * 添加提交
     *
     * @param record 提交对象
     * @return 插入成功的记录条数
     */
    int insert(Solution record);

    /**
     * 选择性添加提交
     *
     * @param record 提交对象
     * @return 插入成功的记录条数
     */
    int insertSelective(Solution record);

    /**
     * 通过ID获取提交对象
     *
     * @param id 提交ID
     * @return 提交对象
     */
    Solution selectById(Integer id);

    /**
     * 通过ID选择性更新提交
     *
     * @param record 提交对象
     * @return 更新成功的记录条数
     */
    int updateByIdSelective(Solution record);

    /**
     * 通过ID更新提交
     *
     * @param record 提交对象
     * @return 更新成功的记录条数
     */
    int updateById(Solution record);


    /**
     * 查询所有提交
     *
     * @return List<Solution> 提交列表
     */
    List<Solution> selectAll();


    /**
     * 查询所有提交并按提交日期降序排序
     *
     * @return List<Solution> 提交列表
     */
    List<Solution> selectAllOrderBySubmitTimeDesc();


    /**
     * 查询所有提交同时返回用户名与题目标题并按提交日期降序排序
     *
     * @return List<SolutionDetail> 提交详情列表
     */
    List<SolutionDetail> selectWithUserAndProblemOrderBySubmitTimeDesc();

    /**
     * 根据关键字查询所有提交同时返回用户名与题目标题并按提交日期降序排序
     *
     * @param keyword 关键字
     * @return List<SolutionDetail> 提交详情列表
     */
    List<SolutionDetail> selectWithUserAndProblemByKeywordOrderBySubmitTimeDesc(String keyword);

}
