package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.dto.DatabaseDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

public interface DatabaseService extends IService<Database> {

    /**
     * 得到所有数据库
     *
     * @param databaseDto databaseDto
     * @param page        page
     * @return IPage<database>
     */
    IPage<DatabaseDto> getAll(DatabaseDto databaseDto, Page page);

    /**
     * 添加数据库
     * @param databaseDto databaseDto
     * @return boolean
     */
    boolean add(DatabaseDto databaseDto);
}
