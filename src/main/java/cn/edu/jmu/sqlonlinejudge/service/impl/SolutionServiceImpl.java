package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.sqlonlinejudge.entity.Solution;
import cn.edu.jmu.sqlonlinejudge.mapper.SolutionMapper;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */

@Service
public class SolutionServiceImpl extends ServiceImpl<SolutionMapper, Solution> implements SolutionService {

    /**
     * 得到所有解答
     *
     * @param solution solution
     * @param page     page
     * @return IPage<solution>
     */
    @Override
    public IPage<Solution> get(Solution solution, Page page) {
        Page<Solution> problemPage = new Page<>(page.getCurrent(), page.getSize());
        return baseMapper.selectPage(problemPage, null);
    }
}
