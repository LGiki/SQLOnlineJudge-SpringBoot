package cn.edu.jmu.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午6:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
     * 题目难度
     */
    private Integer difficulty;

    /**
     * 题目是否是Update/Delete等会对表中记录进行修改的题目
     */
    private Boolean isUpdate;

    /**
     * Update类题目执行完之后对修改部分的Select语句，用于比对修改的结果是否正确
     */
    private String selectAfterUpdate;

    /**
     * 答案
     */
    @NotBlank
    private String answer;
}
