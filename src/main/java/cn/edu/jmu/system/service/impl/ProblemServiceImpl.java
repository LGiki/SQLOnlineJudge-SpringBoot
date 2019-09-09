package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.mapper.ProblemMapper;
import cn.edu.jmu.system.service.ProblemService;
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
     * @param problemDto problemDto
     * @param page       page
     * @return IPage<problem>
     */
    @Override
    public IPage<ProblemDto> getAll(ProblemDto problemDto, Page page) {
        Page<Problem> problemPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Problem> iPage = baseMapper.selectPage(problemPage, predicate(problemDto));
        return iPage.convert(cn.edu.jmu.system.service.mapper.ProblemMapper::toDto);
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
     * 条件构造器
     */
    private LambdaQueryWrapper<Problem> predicate(ProblemDto problemDto) {
        if (problemDto == null) {
            return null;
        } else {
            LambdaQueryWrapper<Problem> queryWrapper = new LambdaQueryWrapper<>();
            if (problemDto.getId() != null) {
                queryWrapper.eq(Problem::getId, problemDto.getId());
                return queryWrapper;
            } else if (problemDto.getTitle() != null) {
                queryWrapper.like(Problem::getTitle, "%" + problemDto.getTitle() + "%");
            }
            return queryWrapper;
        }
    }
}
