package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.sqlonlinejudge.entity.Database;
import cn.edu.jmu.sqlonlinejudge.mapper.DatabaseMapper;
import cn.edu.jmu.sqlonlinejudge.service.DatabaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@Service
public class DatabaseServiceImpl extends ServiceImpl<DatabaseMapper, Database> implements DatabaseService {

    /**
     * 得到所有数据库
     *
     * @param database database
     * @param page     page
     * @return IPage<database>
     */
    @Override
    public IPage<Database> get(Database database, Page page) {
        Page<Database> databasePage = new Page<>(page.getCurrent(), page.getSize());
        return baseMapper.selectPage(databasePage, predicate(database));
    }

    /**
     * 条件构造器
     *
     * @param database database
     * @return LambdaQueryWrapper<database>
     */
    private LambdaQueryWrapper<Database> predicate(Database database) {
        LambdaQueryWrapper<Database> queryWrapper = new LambdaQueryWrapper<>();
        if (database == null) {
            return queryWrapper;
        } else {
            if (database.getId() != null) {
                queryWrapper.eq(Database::getId, database.getId());
                return queryWrapper;
            }
            if (database.getName() != null) {
                queryWrapper.like(Database::getName, "%" + database.getName() + "%");
            }
        }
        return queryWrapper;
    }
}
