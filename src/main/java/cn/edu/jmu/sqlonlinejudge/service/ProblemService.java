package cn.edu.jmu.sqlonlinejudge.service;

import cn.edu.jmu.sqlonlinejudge.entity.Problem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

public interface ProblemService extends IService<Problem> {

    /**
     * 得到所有题目
     *
     * @param problem problem
     * @param page    page
     * @return IPage<problem>
     */
    IPage<Problem> get(Problem problem, Page page);

    /**
     * 更新用户
     *
     * @param problem problem
     * @return boolean
     */
    boolean update(Problem problem);
}
