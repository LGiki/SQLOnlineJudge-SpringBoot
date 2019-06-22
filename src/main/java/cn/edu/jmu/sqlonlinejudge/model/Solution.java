package cn.edu.jmu.sqlonlinejudge.model;

import java.util.Date;
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
    */
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