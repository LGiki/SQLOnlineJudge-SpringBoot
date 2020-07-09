package cn.edu.jmu.system.service;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.system.api.database.DatabaseListResponse;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.dto.DatabaseDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

public interface DatabaseService extends IService<Database> {

    /**
     * 得到数据库列表
     *
     * @param databaseDto databaseDto
     * @param page        page
     * @return IPage<database>
     */
    IPage<DatabaseListResponse> getDatabaseList(DatabaseDto databaseDto, Page page);

    /**
     * 得到全部的数据库列表
     * @return List<DatabaseListResponse> 数据库列表
     */
    List<DatabaseListResponse> getAll();

    /**
     * 添加数据库
     *
     * @param databaseDto databaseDto
     * @return boolean
     */
    JudgeResultJson add(DatabaseDto databaseDto);

    /**
     * 根据数据库ID判断数据库是否存在
     *
     * @param databaseId 数据库ID
     * @return Boolean 数据库是否存在
     */
    Boolean existById(Integer databaseId);
}
