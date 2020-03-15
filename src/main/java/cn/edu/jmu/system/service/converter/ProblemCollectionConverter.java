package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.entity.ProblemCollection;
import cn.edu.jmu.system.entity.dto.ProblemCollectionDto;
import cn.hutool.core.bean.BeanUtil;

/**
 * @author Ethan
 */
public class ProblemCollectionConverter {

    public static ProblemCollectionDto problemCollectionDto(ProblemCollection problemCollection) {
        ProblemCollectionDto problemCollectionDto = new ProblemCollectionDto();
        BeanUtil.copyProperties(problemCollection, problemCollectionDto);
        return problemCollectionDto;
    }
}
