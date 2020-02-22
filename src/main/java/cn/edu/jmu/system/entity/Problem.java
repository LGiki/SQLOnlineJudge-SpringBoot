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
     * 通过数
     */
    @TableField(value = "solved")
    private Integer solved;

    /**
     * 提交数
     */
    @TableField(value = "submit")
    private Integer submit;

    /**
     * 正确输出
     */
    @TableField(value = "true_result")
    private String trueResult;

    /**
     * 数据库ID
     */
    @TableField(value = "database_id")
    @NotBlank
    private Integer databaseId;
}