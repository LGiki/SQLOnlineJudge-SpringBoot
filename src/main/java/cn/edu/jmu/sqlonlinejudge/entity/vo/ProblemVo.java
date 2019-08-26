package cn.edu.jmu.sqlonlinejudge.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午2:22
 */
@Data
public class ProblemVo implements Serializable {

    private static final long serialVersionUID = 200897514465448035L;

    /**
     * 题目ID
     */
    private Integer id;

    /**
     * 题目标题
     */
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
