package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.sqlonlinejudge.entity.Problem;
import cn.edu.jmu.sqlonlinejudge.entity.vo.ProblemVo;
import cn.edu.jmu.sqlonlinejudge.mapper.ProblemMapper;
import cn.edu.jmu.sqlonlinejudge.service.ProblemService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    /**
     * 得到所有题目
     *
     * @param problem problem
     * @param page    page
     * @return IPage<problem>
     */
    @Override
    public IPage<Problem> getAll(Problem problem, Page page) {
        Page<Problem> problemPage = new Page<>(page.getCurrent(), page.getSize());
        return baseMapper.selectPage(problemPage, predicate(problem));
    }

    /**
     * 更新题目
     *
     * @param problem problem
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Problem problem) {
        Problem select = baseMapper.selectById(problem.getId());
        BeanUtil.copyProperties(problem, select, true,
                CopyOptions.create().setIgnoreNullValue(true)
                        .setIgnoreError(true)
                        .setIgnoreProperties("id", "solve", "submit"));
        return baseMapper.updateById(select) >= 1;
    }

    /**
     * 得到所有题目给用户
     *
     * @param problem problem
     * @param page    page
     * @return IPage<problem>
     */
    @Override
    public IPage<ProblemVo> getAllToUser(Problem problem, Page page) {
        Page<Problem> problemPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Problem> iPage = baseMapper.selectPage(problemPage, predicate(problem));
        Page<ProblemVo> res = new Page<ProblemVo>();
        return null;
    }

    /**
     * 条件构造器
     */
    private LambdaQueryWrapper<Problem> predicate(Problem problem) {
        LambdaQueryWrapper<Problem> queryWrapper = new LambdaQueryWrapper<>();
        if (problem == null) {
            return queryWrapper;
        } else {
            if (problem.getId() != null) {
                queryWrapper.eq(Problem::getId, problem.getId());
                return queryWrapper;
            }
            if (problem.getTitle() != null) {
                queryWrapper.like(Problem::getTitle, "%" + problem.getTitle() + "%");
            }
            if (problem.getDatabaseId() != null) {
                queryWrapper.eq(Problem::getDatabaseId, problem.getDatabaseId());
            }
        }
        return queryWrapper;
    }
}
