package cn.edu.jmu.judge.entity.dto;

import cn.edu.jmu.system.service.enums.SolutionResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xeathen
 * @date 2019/9/7 16:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeResultDto implements Serializable {

    private static final long serialVersionUID = -6281476316187992530L;
    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 题目ID
     */
    private Integer pid;

    /**
     * 结果
     */
    private SolutionResultEnum result;

    /**
     * 错误信息
     */
    private String errorMsg;
}
