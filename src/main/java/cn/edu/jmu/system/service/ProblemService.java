package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDetailToUserDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.entity.dto.ProblemListDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
    IPage<ProblemListDto> getAll(ProblemDto problemDto, Page page);

    /**
     * 更新题目
     *
     * @param problem problem
     * @return boolean
     */
    boolean update(Problem problem);

    /**
     * 通过id得到problem给用户
     *
     * @param id id
     * @return ProblemDetailToUserDto
     */
    ProblemDetailToUserDto getToUserById(Integer id);

    /**
     * 通过数据库id获取记录集合
     *
     * @param databaseId
     * @return
     */
    List<Problem> getByDatabaseId(Integer databaseId);

    /**
     * 更新题目List的TrueResult
     *
     * @param problemList
     * @return
     */
    Boolean updateTrueResult(List<Problem> problemList);

    /**
     * 根据题目ID判断题目是否存在
     *
     * @param problemId 题目ID
     * @return Boolean 题目是否存在
     */
    Boolean existById(Integer problemId);
}
