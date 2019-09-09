package cn.edu.jmu.judge.service.impl;

import cn.edu.jmu.judge.service.JudgeService;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.mapper.SolutionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author xeathen
 * @date 2019/9/7 16:45
 */
@Service
public class JudgeServiceImpl  extends ServiceImpl<SolutionMapper, Solution> implements JudgeService {


    @Override
    public boolean judge(SolutionDto solutionDto) {
        return true;
    }
}
