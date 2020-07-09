package cn.edu.jmu.system.api.database;

import lombok.Data;

@Data
public class DatabaseListResponse {
    /**
     * 数据库ID
     */
    private Integer id;

    /**
     * 数据库名称
     */
    private String name;
}
