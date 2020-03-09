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
import cn.edu.jmu.system.service.converter.SolutionConverter;
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
     * 根据用户ID和题目ID获取到用户对某个题目最后一次提交的提交详情
     *
     * @param uid 用户ID
     * @param pid 题目ID
     * @return Solution
     */
    @Override
    public Solution getLatestSolutionByUserIdAndProblemId(Integer uid, Integer pid) {
        if (uid == null || pid == null) {
            return null;
        }
        SolutionDto solutionDto = new SolutionDto();
        Solution solution = baseMapper.selectOne(new QueryWrapper<>(SolutionConverter.toEntity(solutionDto)).lambda().eq(Solution::getUid, uid).eq(Solution::getPid, pid).orderByDesc(Solution::getSubmitTime).last("limit 1"));
        return solution;
    }

    /**
     * 得到所有解答
     */
    @Override
    public IPage<SolutionDto> getAll(SolutionDto solutionDto, Page page) {
        Page<Solution> solutionPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Solution> iPage;
        if (ObjectUtil.isNull(solutionDto)) {
            iPage = baseMapper.selectPage(solutionPage
                    , Wrappers.<Solution>lambdaQuery().orderByDesc(Solution::getId));
        } else {
            if (solutionDto.getId() != null) {
                iPage = baseMapper.selectPage(solutionPage
                        , new QueryWrapper<>(SolutionConverter
                                .toEntity(solutionDto)).lambda().like(Solution::getId, "%" + solutionDto.getId() + "%").orderByDesc(Solution::getId));
            } else if (solutionDto.getUid() != null) {
                iPage = baseMapper.selectPage(solutionPage
                        , new QueryWrapper<>(SolutionConverter
                                .toEntity(solutionDto)).lambda().like(Solution::getUid, "%" + solutionDto.getUid() + "%").orderByDesc(Solution::getId));
            } else if (solutionDto.getPid() != null) {
                iPage = baseMapper.selectPage(solutionPage
                        , new QueryWrapper<>(SolutionConverter
                                .toEntity(solutionDto)).lambda().like(Solution::getPid, "%" + solutionDto.getPid() + "%").orderByDesc(Solution::getId));
            } else {
                iPage = baseMapper.selectPage(solutionPage
                        , Wrappers.<Solution>lambdaQuery().orderByDesc(Solution::getId));
            }
        }
        IPage<SolutionDto> convert = iPage.convert(SolutionConverter::toDto);
        convert.getRecords().forEach(this::addMessage);
        return convert;
    }

    @Override
    public Integer add(SolutionDto solutionDto) {
        //设置状态值
        solutionDto.setResult(SolutionResultEnum.JUDGING);
        //向solution表插入记录
        Solution solution = SolutionConverter.toEntity(solutionDto);
        baseMapper.insert(solution);
        judgeService.judge(SolutionConverter.toDto(solution));
        return solution.getId();
    }

    /**
     * 在solutionDto中增加Username和title
     */
    private void addMessage(SolutionDto solutionDto) {
        solutionDto.setSourceCode(null);
        User user = userService.getById(solutionDto.getUid());
        if (user != null) {
            solutionDto.setUsername(user.getUsername());
            solutionDto.setStudentNo(user.getStudentNo());
        }
        Problem problem = problemService.getById(solutionDto.getPid());
        solutionDto.setTitle(problem.getTitle());
    }
}
