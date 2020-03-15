package cn.edu.jmu.system.service;

import cn.edu.jmu.system.api.problemcollection.CreateProblemCollectionRequest;
import cn.edu.jmu.system.api.problemcollection.CreateProblemCollectionResponse;
import cn.edu.jmu.system.api.problemcollection.DeleteProblemCollectionResponse;
import cn.edu.jmu.system.entity.ProblemCollection;
import cn.edu.jmu.system.entity.dto.ProblemCollectionDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author xeathen
 */
public interface ProblemCollectionService extends IService<ProblemCollection> {
    /**
     * s
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
     * @param id
     * @return
     */
    DeleteProblemCollectionResponse delete(Integer id);
}
