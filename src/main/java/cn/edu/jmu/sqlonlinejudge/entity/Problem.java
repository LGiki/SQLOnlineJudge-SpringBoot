package cn.edu.jmu.sqlonlinejudge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@Data
@TableName(value = "problem")
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
    @TableField(value = "title")
    private String title;

    /**
     * 题目描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 输入格式
     */
    @TableField(value = "input_format")
    private String inputFormat;

    /**
     * 输出格式
     */
    @TableField(value = "output_format")
    private String outputFormat;

    /**
     * 样例输入
     */
    @TableField(value = "sample_input")
    private String sampleInput;

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
    @TableField(value = "answer")
    private String answer;

    /**
     * 通过数
     */
    @TableField(value = "solve")
    private Integer solve;

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
     * 是否需要答案有序
     */
    @TableField(value = "need_order")
    private Boolean needOrder;

    /**
     * 数据库ID
     */
    @TableField(value = "database_id")
    private Integer databaseId;
}