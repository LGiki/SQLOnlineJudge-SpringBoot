package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.enums.CreateDatabaseResultEnum;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.dto.DatabaseDto;
import cn.edu.jmu.system.mapper.DatabaseMapper;
import cn.edu.jmu.system.service.DatabaseService;
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
        return iPage.convert(cn.edu.jmu.system.service.mapper.DatabaseMapper::toDto);
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

    /**
     * 添加数据库
     *
     * @param databaseDto databaseDto
     * @return boolean
     */
    @Override
    public boolean add(DatabaseDto databaseDto) {
        JudgeResultJson judgeResultJson = PythonJudgeUtil.createDatabase(databaseDto.getId());
        return CreateDatabaseResultEnum.OK.getValue().equals(judgeResultJson.code);
    }
}
