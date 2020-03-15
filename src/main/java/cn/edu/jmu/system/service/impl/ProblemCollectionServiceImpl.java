package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.system.api.problemcollection.CreateProblemCollectionRequest;
import cn.edu.jmu.system.api.problemcollection.CreateProblemCollectionResponse;
import cn.edu.jmu.system.api.problemcollection.DeleteProblemCollectionResponse;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.entity.ProblemCollection;
import cn.edu.jmu.system.entity.dto.ProblemCollectionDto;
import cn.edu.jmu.system.mapper.ProblemCollectionMapper;
import cn.edu.jmu.system.service.DatabaseService;
import cn.edu.jmu.system.service.ProblemCategoryService;
import cn.edu.jmu.system.service.ProblemCollectionService;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.converter.ProblemCollectionConverter;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xeathen
 */
@Service
public class ProblemCollectionServiceImpl extends ServiceImpl<ProblemCollectionMapper, ProblemCollection> implements ProblemCollectionService {
    @Resource
    ProblemCategoryService problemCategoryService;
    @Resource
    ProblemService problemService;
    @Resource
    DatabaseService databaseService;

    @Override
    public IPage<ProblemCollectionDto> search(ProblemCollectionDto problemCollectionDto, Page<ProblemCollection> page) {
        Page<ProblemCollection> problemCollectionPage = new Page<>(page.getCurrent(), page.getSize());
        IPage<ProblemCollection> iPage = baseMapper.selectPage(problemCollectionPage, predicate(problemCollectionDto));
        return iPage.convert(this::problemCollectionDto);
    }

    private ProblemCollectionDto problemCollectionDto(ProblemCollection problemCollection) {
        ProblemCollectionDto problemCollectionDto = ProblemCollectionConverter.problemCollectionDto(problemCollection);
        ProblemCategory category = problemCategoryService.getById(problemCollectionDto.getCategoryId());
        problemCollectionDto.setCategoryName(category.getName());
        Problem problem = problemService.getById(problemCollection.getProblemId());
        problemCollectionDto.setProblemTitle(problem.getTitle());
        problemCollectionDto.setProblemDescription(problem.getDescription());
        Database database = databaseService.getById(problem.getDatabaseId());
        problemCollectionDto.setDatabaseId(database.getId());
        problemCollectionDto.setDatabaseName(database.getName());
        return problemCollectionDto;
    }

    private Wrapper<ProblemCollection> predicate(ProblemCollectionDto problemCollectionDto) {
        if (problemCollectionDto == null) {
            return null;
        } else {
            LambdaQueryWrapper<ProblemCollection> queryWrapper = new LambdaQueryWrapper<>();
            if (problemCollectionDto.getId() != null) {
                queryWrapper.eq(ProblemCollection::getId, problemCollectionDto.getId());
                return queryWrapper;
            }
            if (problemCollectionDto.getCategoryId() != null) {
                queryWrapper.eq(ProblemCollection::getCategoryId, problemCollectionDto.getCategoryId());
            }
            if (problemCollectionDto.getProblemId() != null) {
                queryWrapper.eq(ProblemCollection::getProblemId, problemCollectionDto.getProblemId());
            }
            return queryWrapper;
        }
    }

    @Override
    public CreateProblemCollectionResponse create(CreateProblemCollectionRequest request) {
        ProblemCollection problemCollection = new ProblemCollection();
        problemCollection.setCategoryId(request.getCategoryId());
        problemCollection.setProblemId(request.getProblemId());
        baseMapper.insert(problemCollection);
        CreateProblemCollectionResponse response = new CreateProblemCollectionResponse();
        response.setId(problemCollection.getId());
        return response;
    }

    @Override
    public DeleteProblemCollectionResponse delete(Integer id) {
        baseMapper.deleteById(id);
        DeleteProblemCollectionResponse response = new DeleteProblemCollectionResponse();
        response.setId(id);
        return response;
    }
}
