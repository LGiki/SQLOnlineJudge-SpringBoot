package cn.edu.jmu.system.entity.dto;

import cn.edu.jmu.system.service.enums.SolutionResultEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author sgh
 * @date 2019/8/26 下午9:42
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SolutionDto implements Serializable {

    private static final long serialVersionUID = -8322780947820151796L;

    /**
     * 解答ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 题目ID
     */
    @NotNull
    private Integer pid;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 提交时间
     * 格式化显示为 yyyy/MM/dd HH:mm:ss
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp submitTime;

    /**
     * 源代码
     */
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String sourceCode;

    /**
     * 结果
     */
    private SolutionResultEnum result;
}
