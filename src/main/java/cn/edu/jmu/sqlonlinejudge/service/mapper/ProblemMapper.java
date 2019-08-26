package cn.edu.jmu.sqlonlinejudge.service.mapper;

import cn.edu.jmu.sqlonlinejudge.entity.Problem;
import cn.edu.jmu.sqlonlinejudge.entity.dto.ProblemDetailToUserDto;
import cn.edu.jmu.sqlonlinejudge.entity.dto.ProblemDto;
import cn.hutool.core.bean.BeanUtil;

/**
 * @author sgh
 * @date 2019/8/26 下午8:56
 */
public class ProblemMapper {

    public static ProblemDto toDto(Problem problem) {
        ProblemDto problemDto = new ProblemDto();
        BeanUtil.copyProperties(problem, problemDto);
        return problemDto;
    }

    public static ProblemDetailToUserDto toDetailDto(Problem problem) {
        ProblemDetailToUserDto problemDetailToUserDto = new ProblemDetailToUserDto();
        BeanUtil.copyProperties(problem, problemDetailToUserDto);
        return problemDetailToUserDto;
    }

    public static Problem toEntity(ProblemDto problemDto) {
        Problem problem = new Problem();
        BeanUtil.copyProperties(problemDto, problem);
        return problem;
    }
}
