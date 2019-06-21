package cn.edu.jmu.sqlonlinejudge.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.edu.jmu.sqlonlinejudge.mapper.ProblemMapper;
import cn.edu.jmu.sqlonlinejudge.model.Problem;
import cn.edu.jmu.sqlonlinejudge.service.ProblemService;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@Service
public class ProblemServiceImpl implements ProblemService {

    @Resource
    private ProblemMapper problemMapper;

    @Override
    public int deleteById(Integer id) {
        return problemMapper.deleteById(id);
    }

    @Override
    public int insert(Problem record) {
        return problemMapper.insert(record);
    }

    @Override
    public int insertSelective(Problem record) {
        return problemMapper.insertSelective(record);
    }

    @Override
    public Problem selectById(Integer id) {
        return problemMapper.selectById(id);
    }

    @Override
    public int updateByIdSelective(Problem record) {
        return problemMapper.updateByIdSelective(record);
    }

    @Override
    public int updateById(Problem record) {
        return problemMapper.updateById(record);
    }

    @Override
    public List<Problem> selectAll() {
        return problemMapper.selectAll();
    }
}
