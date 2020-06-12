package cn.edu.jmu.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午9:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetailToUserDto extends ProblemDto implements Serializable {

    private static final long serialVersionUID = 3952059359065232418L;

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
     * 样例输出
     */
    private String sampleOutput;

    /**
     * 题目难度
     */
    private Integer difficulty;

    /**
     * 提示
     */
    private String hint;

    /**
     * 建表语句
     */
    private String createTable;
}
