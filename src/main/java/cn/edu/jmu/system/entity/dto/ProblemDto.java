package cn.edu.jmu.system.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private Integer id;

    /**
     * 题目标题
     */
    @NotBlank
    private String title;

    /**
     * 题目难度
     */
    private Integer difficulty;

    /**
     * 数据库ID
     */
    @NotNull
    private Integer databaseId;
}
