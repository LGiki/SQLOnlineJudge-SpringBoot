package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDetailDto;
import cn.edu.jmu.system.entity.dto.ProblemDetailToUserDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author sgh
 * @date 2019/8/26 下午8:56
 */
public class ProblemConverter {

    public static ProblemDto toDto(Problem problem) {
        ProblemDto problemDto = new ProblemDto();
        BeanUtil.copyProperties(problem, problemDto);
        return problemDto;
    }

    public static void toUserDetailDto(Problem problem, ProblemDetailToUserDto problemDetailToUserDto) {
        BeanUtil.copyProperties(problem, problemDetailToUserDto, true, CopyOptions.create()
            .setIgnoreNullValue(true));
    }

    public static ProblemDetailDto toDetail(Problem problem) {
        ProblemDetailDto problemDetailDto = new ProblemDetailDto();
        BeanUtil.copyProperties(problem, problemDetailDto);
        return problemDetailDto;
    }

    public static Problem toEntity(ProblemDetailDto problemDetailDto) {
        Problem problem = new Problem();
        BeanUtil.copyProperties(problemDetailDto, problem, true, CopyOptions.create()
            .setIgnoreNullValue(true)
            .setIgnoreProperties("solve", "submit"));
        return problem;
    }
}
