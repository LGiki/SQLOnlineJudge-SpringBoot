package cn.edu.jmu.sqlonlinejudge.entity.dto;

import cn.edu.jmu.sqlonlinejudge.service.enums.SolutionResultEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author sgh
 * @date 2019/8/26 下午9:42
 */
@Data
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
    private Integer pid;

    /**
     * 题目标题
     */

    private String title;
    /**
     * 提交时间
     * 格式化显示为 yyyy/MM/dd HH:mm:ss
     */
    @JsonFormat(timezone = "UTC", pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp submitTime;

    /**
     * 结果
     */
    private SolutionResultEnum result;
}
