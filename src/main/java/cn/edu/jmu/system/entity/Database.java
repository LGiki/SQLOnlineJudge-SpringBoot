package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */

@Data
@TableName(value = "data_base")
public class Database implements Serializable {

    private static final long serialVersionUID = -8714097362269205387L;

    /**
     * 数据库ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据库名称
     */
    @TableField(value = "name")
    @NotBlank
    private String name;

    /**
     * 建表语句
     */
    @TableField(value = "create_table")
    @NotBlank
    private String createTable;

    /**
     * 数据插入语句
     */
    @TableField(value = "test_data")
    @NotBlank
    private String testData;
}