package cn.edu.jmu.system.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午8:15
 */
@Data
public class DatabaseDto implements Serializable {

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
