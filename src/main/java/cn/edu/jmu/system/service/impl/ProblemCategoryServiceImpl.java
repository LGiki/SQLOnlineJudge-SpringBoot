package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.api.problemcategory.CreateProblemCategoryRequest;
import cn.edu.jmu.system.api.problemcategory.CreateProblemCategoryResponse;
import cn.edu.jmu.system.api.problemcategory.DeleteProblemCategoryResponse;
import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.entity.dto.ProblemCategoryDto;
import cn.edu.jmu.system.mapper.ProblemCategoryMapper;
import cn.edu.jmu.system.service.ProblemCategoryService;
import cn.edu.jmu.system.service.converter.ProblemCategoryConverter;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
        IPage<ProblemCategory> iPage = baseMapper.selectPage(problemCategoryPage, predicate(problemCategoryDto));
        return iPage.convert(ProblemCategoryConverter::problemCategoryDto);
    }

    private Wrapper<ProblemCategory> predicate(ProblemCategoryDto problemCategoryDto) {
        if (problemCategoryDto == null) {
            return null;
        } else {
            LambdaQueryWrapper<ProblemCategory> queryWrapper = new LambdaQueryWrapper<>();
            if (problemCategoryDto.getId() != null) {
                queryWrapper.eq(ProblemCategory::getId, problemCategoryDto.getId());
                return queryWrapper;
            } else if (problemCategoryDto.getName() != null) {
                queryWrapper.like(ProblemCategory::getName, "%" + problemCategoryDto.getName() + "%");
            }
            return queryWrapper;
        }
    }

    @Override
    public CreateProblemCategoryResponse create(CreateProblemCategoryRequest request) {
        ProblemCategory problemCategory = new ProblemCategory();
        problemCategory.setName(request.getName());
        problemCategory.setStartTime(request.getStartTime());
        problemCategory.setEndTime(request.getEndTime());
        problemCategory.setViewAfterEnd(request.getViewAfterEnd());
        baseMapper.insert(problemCategory);
        CreateProblemCategoryResponse response = new CreateProblemCategoryResponse();
        response.setId(problemCategory.getId());
        return response;
    }

    @Override
    public DeleteProblemCategoryResponse delete(Integer id) {
        baseMapper.deleteById(id);
        DeleteProblemCategoryResponse response = new DeleteProblemCategoryResponse();
        response.setId(id);
        return response;
    }
}
