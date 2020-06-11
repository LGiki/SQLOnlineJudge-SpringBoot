package cn.edu.jmu.system.mapper;

import cn.edu.jmu.system.entity.ProblemCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author xeathen
 */
public interface ProblemCollectionMapper extends BaseMapper<ProblemCollection> {
    @Select("SELECT COUNT(1) FROM `problem_collections` where `category_id` = #{problemCategoryId} and `problem_id` = #{problemId}")
    Long countByProblemIdAndProblemCategoryId(Integer problemId, Integer problemCategoryId);
}
