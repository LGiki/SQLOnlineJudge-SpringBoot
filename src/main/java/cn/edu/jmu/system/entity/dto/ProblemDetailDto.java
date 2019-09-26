package cn.edu.jmu.system.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午6:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemDetailDto extends ProblemDto implements Serializable {

    private static final long serialVersionUID = 9025570859172061319L;

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
}
