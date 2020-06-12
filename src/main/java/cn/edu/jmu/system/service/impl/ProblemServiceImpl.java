package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.judge.util.Md5Util;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.ProblemDetailToUserDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.mapper.ProblemMapper;
import cn.edu.jmu.system.service.DatabaseService;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.converter.ProblemConverter;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Resource
    private DatabaseService databaseService;

    /**
     * 得到所有题目
     *
     * @param problemDto problemDto
     * @param page       page
     * @return IPage<problem>
     */
    @Override
    public IPage<ProblemDto> getAll(ProblemDto problemDto, Page page) {
        Page<Problem> problemPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Problem> iPage = baseMapper.selectPage(problemPage, predicate(problemDto).orderByAsc(Problem::getTitle));
        return iPage.convert(ProblemConverter::toDto);
    }

    /**
     * 更新题目
     *
     * @param problem problem
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Problem problem) {
        Problem select = baseMapper.selectById(problem.getId());
        BeanUtil.copyProperties(problem, select, true, CopyOptions.create().setIgnoreError(true).setIgnoreProperties("id", "solve", "submit"));
        return baseMapper.updateById(select) >= 1;
    }

    @Override
    public ProblemDetailToUserDto getToUserById(Integer id) {
        Problem problem = baseMapper.selectById(id);
        if (problem == null) {
            return null;
        }
        Database database = databaseService.getById(problem.getDatabaseId());
        if (database == null) {
            return null;
        }
        ProblemDetailToUserDto problemDetailToUserDto = new ProblemDetailToUserDto();
        problemDetailToUserDto.setCreateTable(database.getCreateTable());
        ProblemConverter.toUserDetailDto(problem, problemDetailToUserDto);
        return problemDetailToUserDto;
    }

    /**
     * 条件构造器
     */
    private LambdaQueryWrapper<Problem> predicate(ProblemDto problemDto) {
        if (problemDto == null) {
            return null;
        } else {
            LambdaQueryWrapper<Problem> queryWrapper = new LambdaQueryWrapper<>();
            if (problemDto.getId() != null) {
                queryWrapper.eq(Problem::getId, problemDto.getId());
            } else if (problemDto.getTitle() != null) {
                queryWrapper.like(Problem::getTitle, "%" + problemDto.getTitle() + "%");
            } else if (problemDto.getDifficulty() != null) {
                queryWrapper.eq(Problem::getDifficulty, problemDto.getDifficulty());
            }
            return queryWrapper;
        }
    }

    @Override
    public List<Problem> getByDatabaseId(Integer databaseId) {
        return baseMapper.selectList(Wrappers.<Problem>lambdaQuery().eq(Problem::getDatabaseId, databaseId));
    }

    @Override
    public Boolean updateTrueResult(List<Problem> problemDtoList) {
        boolean success = true;
        for (Problem problem : problemDtoList) {
            String trueResult = PythonJudgeUtil.getTrueResult(problem.getAnswer(), problem.getDatabaseId())
                    .getData()
                    .getTrueResult();
            String trueResultMd5 = Md5Util.getStringMd5(trueResult);
            problem.setTrueResult(trueResultMd5);
            success = baseMapper.updateById(problem) == 1;
        }
        return success;
    }
}
