package cn.edu.jmu.sqlonlinejudge.service.mapper;

import cn.edu.jmu.sqlonlinejudge.entity.Solution;
import cn.edu.jmu.sqlonlinejudge.entity.dto.SolutionDto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author sgh
 * @date 2019/8/26 下午9:48
 */
public class SolutionMapper {

    public static SolutionDto toDto(Solution solution) {
        SolutionDto solutionDto = new SolutionDto();
        BeanUtil.copyProperties(solution, solutionDto);
        return solutionDto;
    }

    public static Solution toEntity(SolutionDto solutionDto) {
        Solution solution = new Solution();
        BeanUtil.copyProperties(solutionDto, solution, true,
                CopyOptions.create().setIgnoreNullValue(true));
        return solution;
    }
}
