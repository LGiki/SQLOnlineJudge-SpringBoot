package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */
public interface SolutionService extends IService<Solution> {

    /**
     * 根据用户ID、题目集ID、题目ID获取到用户对某个题目最后一次提交的提交详情
     *
     * @param userId            用户ID
     * @param problemCategoryId 题目集ID
     * @param problemId         题目ID
     * @return Solution解答
     */
    public Solution getLatestSubmittedSolution(Integer userId, Integer problemCategoryId, Integer problemId);

    /**
     * 得到所有解答
     *
     * @param solutionDto solutionDto
     * @param page        page
     * @return IPage<solution>
     */
    IPage<SolutionDto> getAll(SolutionDto solutionDto, Page page);

    /**
     * 增加solution
     *
     * @param solutionDto solutionDto
     * @return boolean
     */
    Integer add(SolutionDto solutionDto);
}
