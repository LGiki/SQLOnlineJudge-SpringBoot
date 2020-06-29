package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@Data
@TableName(value = "problems")
public class Problem implements Serializable {

    private static final long serialVersionUID = -1984904930642872739L;

    /**
     * 题目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题目标题
     */
    @NotBlank
    @TableField(value = "title")
    private String title;

    /**
     * 题目描述
     */
    @NotBlank
    @TableField(value = "description")
    private String description;

    /**
     * 样例输出
     */
    @TableField(value = "sample_output")
    private String sampleOutput;

    /**
     * 提示
     */
    @TableField(value = "hint")
    private String hint;

    /**
     * 答案
     */
    @NotBlank
    @TableField(value = "answer")
    private String answer;

    /**
     * 正确输出
     */
    @TableField(value = "true_result")
    private String trueResult;

    /**
     * 题目难度
     */
    @TableField(value = "difficulty")
    private Integer difficulty;

    /**
     * 题目是否是Update/Delete等会对表中记录进行修改的题目
     */
    @TableField(value = "is_update")
    private Boolean isUpdate;

    /**
     * Update类题目执行完之后对修改部分的Select语句，用于比对修改的结果是否正确
     */
    @TableField(value = "select_after_update", updateStrategy = FieldStrategy.IGNORED)
    private String selectAfterUpdate;

    /**
     * 数据库ID
     */
    @TableField(value = "database_id")
    @NotBlank
    private Integer databaseId;
}