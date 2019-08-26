package cn.edu.jmu.sqlonlinejudge.service.impl;

import cn.edu.jmu.sqlonlinejudge.entity.Database;
import cn.edu.jmu.sqlonlinejudge.entity.dto.DatabaseDto;
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
     * @param databaseDto databaseDto
     * @param page        page
     * @return IPage<database>
     */
    @Override
    public IPage<DatabaseDto> getAll(DatabaseDto databaseDto, Page page) {
        Page<Database> databasePage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Database> iPage = baseMapper.selectPage(databasePage, predicate(databaseDto));
        return iPage.convert(cn.edu.jmu.sqlonlinejudge.service.mapper.DatabaseMapper::toDto);
    }

    /**
     * 条件构造器
     *
     * @param databaseDto databaseDto
     * @return LambdaQueryWrapper<database>
     */
    private LambdaQueryWrapper<Database> predicate(DatabaseDto databaseDto) {
        LambdaQueryWrapper<Database> queryWrapper = new LambdaQueryWrapper<>();
        if (databaseDto == null) {
            return queryWrapper;
        } else {
            if (databaseDto.getId() != null) {
                queryWrapper.eq(Database::getId, databaseDto.getId());
            } else if (databaseDto.getName() != null) {
                queryWrapper.like(Database::getName, "%" + databaseDto.getName() + "%");
            }
            return queryWrapper;
        }
    }
}
