package cn.edu.jmu.sqlonlinejudge.model;

import lombok.Data;

/**
 * @author LGiki
 * @date 2019/06/21 13:19
 */

@Data
public class Problem {
    /**
     * 题目ID
     */
    private Integer id;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 输入格式
     */
    private String inputFormat;

    /**
     * 输出格式
     */
    private String outputFormat;

    /**
     * 样例输入
     */
    private String sampleInput;

    /**
     * 样例输出
     */
    private String sampleOutput;

    /**
     * 提示
     */
    private String hint;

    /**
     * 答案
     */
    private String answer;

    /**
     * 通过数
     */
    private Integer solve;

    /**
     * 提交数
     */
    private Integer submit;

    /**
     * 正确输出
     */
    private String trueResult;

    /**
     * 是否需要答案有序
     */
    private Boolean needOrder;

    /**
     * 数据库ID
     */
    private Integer databaseId;
}