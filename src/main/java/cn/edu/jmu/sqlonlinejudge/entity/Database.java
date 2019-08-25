package cn.edu.jmu.sqlonlinejudge.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@Data
public class Database implements Serializable {

    private static final long serialVersionUID = -8714097362269205387L;
    /**
     * 数据库ID
     */
    private Integer id;

    /**
     * 数据库名称
     */
    @NotBlank
    private String name;

    /**
     * 建表语句
     */
    @NotBlank
    private String createTable;

    /**
     * 数据插入语句
     */
    @NotBlank
    private String testData;

    /**
     * 数据库是否已创建
     */
    private Boolean isCreated;
}