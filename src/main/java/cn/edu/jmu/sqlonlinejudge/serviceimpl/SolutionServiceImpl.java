package cn.edu.jmu.sqlonlinejudge.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.edu.jmu.sqlonlinejudge.mapper.SolutionMapper;
import cn.edu.jmu.sqlonlinejudge.model.Solution;
import cn.edu.jmu.sqlonlinejudge.service.SolutionService;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */

@Service
public class SolutionServiceImpl implements SolutionService {

    @Resource
    private SolutionMapper solutionMapper;

    @Override
    public int deleteById(Integer id) {
        return solutionMapper.deleteById(id);
    }

    @Override
    public int insert(Solution record) {
        return solutionMapper.insert(record);
    }

    @Override
    public int insertSelective(Solution record) {
        return solutionMapper.insertSelective(record);
    }

    @Override
    public Solution selectById(Integer id) {
        return solutionMapper.selectById(id);
    }

    @Override
    public int updateByIdSelective(Solution record) {
        return solutionMapper.updateByIdSelective(record);
    }

    @Override
    public int updateById(Solution record) {
        return solutionMapper.updateById(record);
    }

    @Override
    public List<Solution> selectAll() {
        return solutionMapper.selectAll();
    }


}
