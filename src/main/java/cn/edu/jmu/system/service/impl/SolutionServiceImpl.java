package cn.edu.jmu.system.service.impl;

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
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */

@Service
@Slf4j
public class SolutionServiceImpl extends ServiceImpl<SolutionMapper, Solution> implements SolutionService {

    @Resource
    private UserService userService;

    @Resource
    private ProblemService problemService;

    @Resource
    private JudgeService judgeService;

    /**
     * 得到所有解答
     */
    @Override
    public IPage<SolutionDto> get(SolutionDto solutionDto, Page page) {
        Page<Solution> solutionPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Solution> iPage;
        if (ObjectUtil.isNull(solutionDto)) {
            iPage = baseMapper.selectPage(solutionPage
                    , Wrappers.<Solution>lambdaQuery().orderByDesc(Solution::getId));
        } else {
            iPage = baseMapper.selectPage(solutionPage
                    , new QueryWrapper<>(cn.edu.jmu.system.service.mapper.SolutionMapper
                            .toEntity(solutionDto)).orderByDesc());
        }
        IPage<SolutionDto> convert = iPage.convert(cn.edu.jmu.system.service.mapper.SolutionMapper::toDto);
        convert.getRecords().forEach(this::addMessage);
        return convert;
    }

    @Override
    public boolean add(SolutionDto solutionDto) {
        //设置状态值
        solutionDto.setResult(SolutionResultEnum.JUDGING);
        //向solution表插入记录
        Solution solution = cn.edu.jmu.system.service.mapper.SolutionMapper.toEntity(solutionDto);
        int num = baseMapper.insert(solution);
        judgeService.judge(cn.edu.jmu.system.service.mapper.SolutionMapper.toDto(solution));
        return num >= 1;
    }

    /**
     * 在solutionDto中增加Username和title
     */
    private void addMessage(SolutionDto solutionDto) {
        solutionDto.setSourceCode(null);
        User user = userService.getById(solutionDto.getUid());
        solutionDto.setUsername(user.getUsername());
        Problem problem = problemService.getById(solutionDto.getPid());
        solutionDto.setTitle(problem.getTitle());
    }
}
