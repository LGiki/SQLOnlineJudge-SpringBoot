package cn.edu.jmu.sqlonlinejudge.service;

import cn.edu.jmu.sqlonlinejudge.entity.Solution;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */
public interface SolutionService extends IService<Solution> {

    /**
     * 得到所有解答
     *
     * @param solution solution
     * @param page     page
     * @return IPage<solution>
     */
    IPage<Solution> get(Solution solution, Page page);
}
