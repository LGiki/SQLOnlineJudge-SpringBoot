package cn.edu.jmu.sqlonlinejudge.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午6:45
 */
@Data
public class ProblemDetailDto implements Serializable {

    private static final long serialVersionUID = 9025570859172061319L;

    /**
     * 题目ID
     */
    private Integer id;

    /**
     * 题目标题
     */
    @NotBlank
    private String title;

    /**
     * 题目描述
     */
    @NotBlank
    private String description;

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
    @NotBlank
    private String answer;

    /**
     * 数据库ID
     */
    @NotBlank
    private Integer databaseId;
}
