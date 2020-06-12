package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.entity.dto.ProblemCategoryDto;
import cn.hutool.core.bean.BeanUtil;

import java.util.Date;

/**
 * @author xeathen
 */
public class ProblemCategoryConverter {

    public static ProblemCategoryDto problemCategoryDto(ProblemCategory problemCategory) {
        ProblemCategoryDto problemCategoryDto = new ProblemCategoryDto();
        BeanUtil.copyProperties(problemCategory, problemCategoryDto);
        Date startTime = problemCategory.getStartTime();
        Date endTime = problemCategory.getEndTime();
        if (startTime != null && endTime != null) {
            Date date = new Date();
            long currentTimestamp = date.getTime();
            long startTimestamp = startTime.getTime();
            long endTimestamp = endTime.getTime();
            if (currentTimestamp < startTimestamp) {
                problemCategoryDto.setStatus("未开始");
            } else if (currentTimestamp <= endTimestamp) {
                problemCategoryDto.setStatus("正在进行");
            } else {
                if (problemCategory.getViewAfterEnd()) {
                    problemCategoryDto.setStatus("已结束，可以查看题目");
                } else {
                    problemCategoryDto.setStatus("已结束，不允许查看题目");
                }
            }
        } else {
            problemCategoryDto.setStatus("未知");
        }
        return problemCategoryDto;
    }
}
