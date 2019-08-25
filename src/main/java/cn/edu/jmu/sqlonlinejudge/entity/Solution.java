package cn.edu.jmu.sqlonlinejudge.entity;

import cn.edu.jmu.sqlonlinejudge.service.enums.SolutionResultEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */

@Data
public class Solution implements Serializable {

    private static final long serialVersionUID = -1536199246828489364L;

    /**
     * 解答ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 题目ID
     */
    private Integer pid;

    /**
     * 源代码
     */
    private String sourceCode;

    /**
     * 提交时间
     * 格式化显示为 yyyy/MM/dd HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date submitTime;

    /**
     * 错误信息
     */
    private String runError;

    /**
     * 结果
     */
    private SolutionResultEnum result;
}