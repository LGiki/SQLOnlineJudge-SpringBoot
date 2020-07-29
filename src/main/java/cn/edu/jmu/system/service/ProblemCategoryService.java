package cn.edu.jmu.system.service;

import cn.edu.jmu.system.api.problemcategory.DeleteProblemCategoryResponse;
import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.entity.dto.ProblemCategoryDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xeathen
 */
public interface ProblemCategoryService extends IService<ProblemCategory> {
    /**
     * 查询题目集
     *
     * @param problemCategoryDto
     * @param page
     * @return
     */
    IPage<ProblemCategoryDto> search(ProblemCategoryDto problemCategoryDto, Page<ProblemCategory> page);

    /**
     * 创建题目集
     *
     * @param problemCategory
     * @return
     */
    Integer create(ProblemCategory problemCategory);

    /**
     * 删除题目集
     *
     * @param id
     * @return
     */
    DeleteProblemCategoryResponse delete(Integer id);

    /**
     * 题目集是否存在
     *
     * @param id 题目集ID
     * @return Boolean 是否存在
     */
    Boolean existById(Integer id);
}
