package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.judge.executor.ThreadPoolUtils;
import cn.edu.jmu.judge.executor.thread.JudgeThread;
import cn.edu.jmu.judge.service.JudgeService;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.mapper.SolutionMapper;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.SolutionService;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.enums.SolutionResultEnum;
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

    @Resource
    private JudgeService judgeService;

    /**
     * 得到所有解答
     *
     * @param solutionDto solutionDto
     * @param page        page
     * @return IPage<solution>
     */
    @Override
    public IPage<SolutionDto> get(SolutionDto solutionDto, Page page) {
        Page<Solution> solutionPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Solution> iPage = baseMapper.selectPage(solutionPage, Wrappers.<Solution>lambdaQuery().orderByDesc(Solution::getSubmitTime));
        IPage<SolutionDto> convert = iPage.convert(cn.edu.jmu.system.service.mapper.SolutionMapper::toDto);
        convert.getRecords().forEach(this::addMessage);
        return convert;
    }

    @Override
    public boolean add(SolutionDto solutionDto) {
        //add
        solutionDto.setResult(SolutionResultEnum.UNKNOWN);
        baseMapper.insert(cn.edu.jmu.system.service.mapper.SolutionMapper.toEntity(solutionDto));
        try {
            ThreadPoolUtils.getInstance().submit(new JudgeThread(solutionDto.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    private void addMessage(SolutionDto solutionDto) {
        User user = userService.getById(solutionDto.getUid());
        solutionDto.setUsername(user.getUsername());
        Problem problem = problemService.getById(solutionDto.getPid());
        solutionDto.setTitle(problem.getTitle());
    }
}
