package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

public interface ProblemService extends IService<Problem> {

    /**
     * 得到所有题目
     *
     * @param problemDto problemDto
     * @param page       page
     * @return IPage<problem>
     */
    IPage<ProblemDto> getAll(ProblemDto problemDto, Page page);

    /**
     * 更新用户
     *
     * @param problem problem
     * @return boolean
     */
    boolean update(Problem problem);
}
