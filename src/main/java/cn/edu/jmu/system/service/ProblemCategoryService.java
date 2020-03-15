package cn.edu.jmu.system.service;

import cn.edu.jmu.system.api.problemcategory.CreateProblemCategoryRequest;
import cn.edu.jmu.system.api.problemcategory.CreateProblemCategoryResponse;
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
     * 查询题目分类
     *
     * @param problemCategoryDto
     * @param page
     * @return
     */
    IPage<ProblemCategoryDto> search(ProblemCategoryDto problemCategoryDto, Page<ProblemCategory> page);

    /**
     * 创建题目分类
     *
     * @param request
     * @return
     */
    CreateProblemCategoryResponse create(CreateProblemCategoryRequest request);

    /**
     * 删除题目分类
     *
     * @param id
     * @return
     */
    DeleteProblemCategoryResponse delete(Integer id);
}
