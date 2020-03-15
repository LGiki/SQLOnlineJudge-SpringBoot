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
@TableName(value = "problem_collections")
public class ProblemCollection implements Serializable {
    private static final long serialVersionUID = 6591142496086146966L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "category_id")
    private Integer categoryId;

    @TableField(value = "problem_id")
    private Integer problemId;
}
