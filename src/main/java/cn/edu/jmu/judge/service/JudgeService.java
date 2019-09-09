package cn.edu.jmu.judge.service;

import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xeathen
 * @date 2019/9/7 16:41
 */
public interface JudgeService extends IService<Solution> {

    /**
     * 判题
     *
     * @param solutionDto
     * @return boolean
     */
    boolean judge(SolutionDto solutionDto);


}
