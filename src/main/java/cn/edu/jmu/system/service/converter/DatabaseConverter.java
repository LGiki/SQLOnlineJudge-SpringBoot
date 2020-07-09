package cn.edu.jmu.system.service.converter;

import cn.edu.jmu.system.api.database.DatabaseListResponse;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.dto.DatabaseDto;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * @author sgh
 * @date 2019/8/26 下午8:36
 */
public class DatabaseConverter {

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

    public static DatabaseListResponse toDatabaseListResponse(Database database) {
        DatabaseListResponse databaseListResponse = new DatabaseListResponse();
        BeanUtil.copyProperties(database, databaseListResponse);
        return databaseListResponse;
    }
}
