package cn.edu.jmu.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/10/8 16:31
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolutionCodeDto implements Serializable {

    private static final long serialVersionUID = -583815554799269043L;

    /**
     * 源代码
     */
    private String sourceCode;

    /**
     * 错误信息
     */
    private String runError;
}
