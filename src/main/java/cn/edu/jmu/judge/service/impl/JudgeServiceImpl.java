package cn.edu.jmu.judge.service.impl;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.enums.JudgeResponseCodeEnum;
import cn.edu.jmu.judge.service.JudgeService;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.mapper.ProblemMapper;
import cn.edu.jmu.system.mapper.SolutionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xeathen
 * @date 2019/9/7 16:45
 */
@Service
public class JudgeServiceImpl extends ServiceImpl<SolutionMapper, Solution> implements JudgeService {


    @Resource
    private ProblemMapper problemMapper;

    /**
     * 判题
     *
     * @param solutionDto solutionDto
     * @return boolean
     */
    @Override
    public boolean judge(SolutionDto solutionDto) {
        //判题
        JudgeResultJson judgeResultJson = PythonJudgeUtil.sqlJudge(solutionDto.getId());
        if (judgeResultJson == null) {
            return false;
        }

        //
        return true;
    }

    /**
     * 获取正确答案
     *
     * @param problemId problemId
     * @return boolean
     */
    @Override
    public String getTrueResult(Integer problemId) {
        JudgeResultJson judgeResultJson = PythonJudgeUtil.getTrueResult(problemId);
        if (JudgeResponseCodeEnum.OK.getValue().equals(judgeResultJson.code)) {
            return judgeResultJson.data.trueResult;
        }
        return null;
    }
}
