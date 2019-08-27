package cn.edu.jmu.sqlonlinejudge.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午8:46
 */
@Data
public class ProblemDto implements Serializable {

    private static final long serialVersionUID = 4508740922344036666L;

    /**
     * 题目ID
     */
    @NotBlank
    private Integer id;

    /**
     * 题目标题
     */
    @NotBlank
    private String title;

    /**
     * 通过数
     */
    private Integer solve;

    /**
     * 提交数
     */
    private Integer submit;
}
