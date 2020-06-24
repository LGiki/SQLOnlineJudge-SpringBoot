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

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题目集ID
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 题目ID
     */
    @TableField(value = "problem_id")
    private Integer problemId;

    /**
     * 题目的分值
     */
    @TableField(value = "problem_score")
    private Integer problemScore;
}
