package cn.edu.jmu.system.service.impl;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import cn.edu.jmu.system.api.database.DatabaseListResponse;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.dto.DatabaseDto;
import cn.edu.jmu.system.mapper.DatabaseMapper;
import cn.edu.jmu.system.service.DatabaseService;
import cn.edu.jmu.system.service.converter.DatabaseConverter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public IPage<DatabaseListResponse> getDatabaseList(DatabaseDto databaseDto, Page page) {
        Page<Database> databasePage = new Page<>(page.getCurrent(), page.getSize());
        IPage<Database> iPage = baseMapper.selectPage(databasePage, predicate(databaseDto));
        return iPage.convert(DatabaseConverter::toDatabaseListResponse);
    }

    /**
     * 得到全部的数据库列表
     *
     * @return List<DatabaseListResponse> 数据库列表
     */
    @Override
    public List<DatabaseListResponse> getAll() {
        return baseMapper.selectList(Wrappers.<Database>lambdaQuery()).stream().map(DatabaseConverter::toDatabaseListResponse).collect(Collectors.toList());
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
    public JudgeResultJson add(DatabaseDto databaseDto) {
        JudgeResultJson judgeResultJson = PythonJudgeUtil.createDatabase(databaseDto.getId(), databaseDto.getCreateTable(), databaseDto.getTestData());
        log.debug(judgeResultJson.toString());
        return judgeResultJson;
    }

    @Override
    public Boolean existById(Integer databaseId) {
        return baseMapper.selectCount(Wrappers.<Database>lambdaQuery().eq(Database::getId, databaseId)) != 0;
    }
}
