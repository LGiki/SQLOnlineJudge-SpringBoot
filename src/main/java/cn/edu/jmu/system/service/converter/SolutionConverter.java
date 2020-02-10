package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.dto.SolutionCodeDto;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author sgh
 * @date 2019/8/26 下午9:48
 */
public class SolutionConverter {

    private SolutionConverter() {
    }

    public static SolutionDto toDto(Solution solution) {
        SolutionDto solutionDto = new SolutionDto();
        BeanUtil.copyProperties(solution, solutionDto, true, CopyOptions.create().setIgnoreNullValue(true));
        return solutionDto;
    }

    public static Solution toEntity(SolutionDto solutionDto) {
        Solution solution = new Solution();
        BeanUtil.copyProperties(solutionDto, solution, true, CopyOptions.create().setIgnoreNullValue(true));
        return solution;
    }

    public static SolutionCodeDto toSolutionCodeDto(Solution solution) {
        SolutionCodeDto solutionCodeDto = new SolutionCodeDto();
        solutionCodeDto.setRunError(solution.getRunError());
        solutionCodeDto.setSourceCode(solution.getSourceCode());
        return solutionCodeDto;
    }
}
