package cn.edu.jmu.system.service;

import cn.edu.jmu.system.api.problemcollection.CreateProblemCollectionRequest;
import cn.edu.jmu.system.api.problemcollection.CreateProblemCollectionResponse;
import cn.edu.jmu.system.entity.ProblemCollection;
import cn.edu.jmu.system.entity.dto.ProblemCollectionDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author xeathen
 */
public interface ProblemCollectionService extends IService<ProblemCollection> {
    /**
     * 搜索题目集关系
     *
     * @param problemCollectionDto
     * @param page
     * @return
     */
    IPage<ProblemCollectionDto> search(ProblemCollectionDto problemCollectionDto, Page<ProblemCollection> page);

    /**
     * 创建题目集关系
     *
     * @param request
     * @return
     */
    CreateProblemCollectionResponse create(CreateProblemCollectionRequest request);

    /**
     * 删除题目集关系
     *
     * @param id ProblemCollection ID
     * @return Boolean 是否删除成功
     */
    Boolean delete(Integer id);

    /**
     * 判断某个problemId对应的题目是否存在于problemCategoryId对应的题目集里
     *
     * @param problemId         题目ID
     * @param problemCategoryId 题目集ID
     * @return Boolean 题目是否存在于题目集里
     */
    Boolean isProblemInProblemCollection(Integer problemId, Integer problemCategoryId);

    /**
     * 根据题目集ID获取该题目集的所有题目ID
     *
     * @param problemCategoryId 题目集ID
     * @return List<Integer> 题目ID集合
     */
    List<Integer> getProblemIdsByProblemCategoryId(Integer problemCategoryId);

    /**
     * 通过ProblemCollection ID更新题目分值
     *
     * @param id           ProblemCollection ID
     * @param problemScore 新的题目分值
     * @return Boolean 是否更新成功
     */
    Boolean updateProblemScoreById(Integer id, Integer problemScore);

    /**
     * 通过Problem Collection ID判断Problem Collection是否存在
     *
     * @param id Problem Collection ID
     * @return Boolean Problem Collection是否存在
     */
    Boolean existById(Integer id);
}
