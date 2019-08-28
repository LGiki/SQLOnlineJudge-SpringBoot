package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.sqlonlinejudge.entity.Problem;
import cn.edu.jmu.sqlonlinejudge.entity.Solution;
import cn.edu.jmu.sqlonlinejudge.entity.User;
import cn.edu.jmu.sqlonlinejudge.entity.dto.SolutionDto;
import cn.edu.jmu.sqlonlinejudge.mapper.SolutionMapper;
import cn.edu.jmu.sqlonlinejudge.service.ProblemService;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */

@Service
public class SolutionServiceImpl extends ServiceImpl<SolutionMapper, Solution> implements SolutionService {

    @Resource
    private UserService userService;

    @Resource
    private ProblemService problemService;

    /**
     * 得到所有解答
     *
     * @param solutionDto solutionDto
     * @param page        page
     * @return IPage<solution>
     */
    @Override
    public IPage<SolutionDto> get(SolutionDto solutionDto, Page page) {
        Page<Solution> problemPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Solution> iPage = baseMapper.selectPage(problemPage, Wrappers.<Solution>lambdaQuery().orderByDesc(Solution::getSubmitTime));
        IPage<SolutionDto> convert = iPage.convert(cn.edu.jmu.sqlonlinejudge.service.mapper.SolutionMapper::toDto);
        convert.getRecords().forEach(this::addMessage);
        return convert;
    }

    @Override
    public boolean add(SolutionDto solutionDto) {
        return true;
    }

    private void addMessage(SolutionDto solutionDto) {
        User user = userService.getById(solutionDto.getUid());
        solutionDto.setUsername(user.getUsername());
        Problem problem = problemService.getById(solutionDto.getPid());
        solutionDto.setTitle(problem.getTitle());
    }
}
