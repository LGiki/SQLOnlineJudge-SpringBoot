package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.entity.dto.ProblemCategoryDto;
import cn.hutool.core.bean.BeanUtil;

/**
 * @author xeathen
 */
public class ProblemCategoryConverter {

    public static ProblemCategoryDto problemCategoryDto(ProblemCategory problemCategory) {
        ProblemCategoryDto problemCategoryDto = new ProblemCategoryDto();
        BeanUtil.copyProperties(problemCategory, problemCategoryDto);
        return problemCategoryDto;
    }
}
