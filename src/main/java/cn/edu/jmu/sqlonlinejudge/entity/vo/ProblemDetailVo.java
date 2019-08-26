package cn.edu.jmu.sqlonlinejudge.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午2:18
 */
@Data
public class ProblemDetailVo implements Serializable {


    private static final long serialVersionUID = -1984955049542872739L;

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
     * 通过数
     */
    private Integer solve;

    /**
     * 提交数
     */
    private Integer submit;
}
