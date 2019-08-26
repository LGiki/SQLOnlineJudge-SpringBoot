package cn.edu.jmu.sqlonlinejudge.service.mapper;

import cn.edu.jmu.sqlonlinejudge.entity.Database;
import cn.edu.jmu.sqlonlinejudge.entity.dto.DatabaseDto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author sgh
 * @date 2019/8/26 下午8:36
 */
public class DatabaseMapper {

    public static DatabaseDto toDto(Database database) {
        DatabaseDto databaseDto = new DatabaseDto();
        BeanUtil.copyProperties(database, databaseDto);
        return databaseDto;
    }

    public static Database toEntity(DatabaseDto databaseDto) {
        Database database = new Database();
        BeanUtil.copyProperties(databaseDto, database, true, CopyOptions.create().setIgnoreNullValue(true));
        return database;
    }
}
