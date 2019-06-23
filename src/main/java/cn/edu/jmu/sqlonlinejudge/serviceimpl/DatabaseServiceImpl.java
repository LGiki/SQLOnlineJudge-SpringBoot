package cn.edu.jmu.sqlonlinejudge.serviceimpl;
import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.edu.jmu.sqlonlinejudge.mapper.DatabaseMapper;
import cn.edu.jmu.sqlonlinejudge.model.Database;
import cn.edu.jmu.sqlonlinejudge.service.DatabaseService;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@Service
public class DatabaseServiceImpl implements DatabaseService{

    @Resource
    private DatabaseMapper databaseMapper;

    @Override
    public int deleteById(Integer id) {
        return databaseMapper.deleteById(id);
    }

    @Override
    public int insert(Database record) {
        return databaseMapper.insert(record);
    }

    @Override
    public int insertSelective(Database record) {
        return databaseMapper.insertSelective(record);
    }

    @Override
    public Database selectById(Integer id) {
        return databaseMapper.selectById(id);
    }

    @Override
    public int updateByIdSelective(Database record) {
        return databaseMapper.updateByIdSelective(record);
    }

    @Override
    public int updateById(Database record) {
        return databaseMapper.updateById(record);
    }

	@Override
	public List<Database> findAll(){
		 return databaseMapper.findAll();
	}

}
