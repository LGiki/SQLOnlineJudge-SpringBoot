package cn.edu.jmu.system.mapper;

import cn.edu.jmu.system.entity.ProblemCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author xeathen
 */
public interface ProblemCollectionMapper extends BaseMapper<ProblemCollection> {
    @Select("SELECT COUNT(1) FROM `problem_collections` where `category_id` = #{problemCategoryId} and `problem_id` = #{problemId}")
    Long countByProblemIdAndProblemCategoryId(Integer problemId, Integer problemCategoryId);

    @Update("UPDATE `problem_collections` set `problem_score` = #{newProblemScore} where `id` = #{id}")
    Integer updateProblemScoreById(Integer id, Integer newProblemScore);
}
