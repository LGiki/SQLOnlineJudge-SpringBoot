package cn.edu.jmu.sqlonlinejudge.mapper;

import cn.edu.jmu.sqlonlinejudge.model.Database;

import java.util.List;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

public interface DatabaseMapper {
    /**
     * 通过ID删除数据库
     *
     * @param id 数据库ID
     * @return 删除成功的记录条数
     */
    int deleteById(Integer id);

    /**
     * 添加数据库
     *
     * @param record 数据库对象
     * @return 添加成功的记录条数
     */
    int insert(Database record);

    /**
     * 选择性添加数据库
     *
     * @param record 数据库对象
     * @return 添加成功的记录条数
     */
    int insertSelective(Database record);

    /**
     * 通过ID获取数据库信息
     *
     * @param id 数据库ID
     * @return 数据库对象
     */
    Database selectById(Integer id);

    /**
     * 通过ID选择性更新数据库
     *
     * @param record 数据库对象
     * @return 更新成功的记录条数
     */
    int updateByIdSelective(Database record);

    /**
     * 通过ID更新数据库
     *
     * @param record 数据库对象
     * @return 更新成功的记录条数
     */
    int updateById(Database record);


    /**
     * 查询所有数据库
     *
     * @return List<Database> 数据库列表
     */
    List<Database> findAll();

}