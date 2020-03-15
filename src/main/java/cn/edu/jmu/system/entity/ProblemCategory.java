package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xeathen
 */
@Data
@TableName(value = "problem_categories")
public class ProblemCategory implements Serializable {
    private static final long serialVersionUID = 7126714545199482422L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    protected String name;
}
