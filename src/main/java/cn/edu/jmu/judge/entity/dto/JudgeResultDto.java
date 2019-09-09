package cn.edu.jmu.judge.entity.dto;

import cn.edu.jmu.system.service.enums.SolutionResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xeathen
 * @date 2019/9/7 16:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeResultDto {

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
