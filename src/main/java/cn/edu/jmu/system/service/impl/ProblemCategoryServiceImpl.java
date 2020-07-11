package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.api.problemcategory.DeleteProblemCategoryResponse;
import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.entity.dto.ProblemCategoryDto;
import cn.edu.jmu.system.mapper.ProblemCategoryMapper;
import cn.edu.jmu.system.service.ProblemCategoryService;
import cn.edu.jmu.system.service.converter.ProblemCategoryConverter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author xeathen
 */
@Service
public class ProblemCategoryServiceImpl extends ServiceImpl<ProblemCategoryMapper, ProblemCategory> implements ProblemCategoryService {

    @Override
    public IPage<ProblemCategoryDto> search(ProblemCategoryDto problemCategoryDto, Page<ProblemCategory> page) {
        Page<ProblemCategory> problemCategoryPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<ProblemCategory> iPage = baseMapper.selectPage(problemCategoryPage, predicate(problemCategoryDto).orderByDesc(ProblemCategory::getId));
        return iPage.convert(ProblemCategoryConverter::problemCategoryDto);
    }

    private LambdaQueryWrapper<ProblemCategory> predicate(ProblemCategoryDto problemCategoryDto) {
        if (problemCategoryDto == null) {
            return null;
        } else {
            LambdaQueryWrapper<ProblemCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            if (problemCategoryDto.getId() != null) {
                lambdaQueryWrapper.eq(ProblemCategory::getId, problemCategoryDto.getId());
            } else if (problemCategoryDto.getName() != null) {
                lambdaQueryWrapper.like(ProblemCategory::getName, "%" + problemCategoryDto.getName() + "%");
            }
            return lambdaQueryWrapper;
        }
    }

    @Override
    public Integer create(ProblemCategory problemCategory) {
        problemCategory.setId(null);
        baseMapper.insert(problemCategory);
        return problemCategory.getId();
    }

    @Override
    public DeleteProblemCategoryResponse delete(Integer id) {
        baseMapper.deleteById(id);
        DeleteProblemCategoryResponse response = new DeleteProblemCategoryResponse();
        response.setId(id);
        return response;
    }

    @Override
    public Boolean existById(Integer id) {
        return baseMapper.selectCount(Wrappers.<ProblemCategory>lambdaQuery().eq(ProblemCategory::getId, id)) != 0;
    }
}
