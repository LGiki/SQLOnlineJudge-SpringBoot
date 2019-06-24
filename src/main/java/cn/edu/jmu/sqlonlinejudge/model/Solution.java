package cn.edu.jmu.sqlonlinejudge.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Solution {
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
    private Byte result;
}