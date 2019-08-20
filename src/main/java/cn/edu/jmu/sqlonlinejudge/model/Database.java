package cn.edu.jmu.sqlonlinejudge.model;

import lombok.Data;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@Data
public class Database {
    /**
     * 数据库ID
     */
    private Integer id;

    /**
     * 数据库名称
     */
    private String name;

    /**
     * 建表语句
     */
    private String createTable;

    /**
     * 数据插入语句
     */
    private String testData;

    /**
     * 数据库是否已创建
     */
    private Boolean isCreated;
}