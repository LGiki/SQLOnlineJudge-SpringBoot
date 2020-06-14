package cn.edu.jmu.system.entity;

import cn.edu.jmu.system.service.enums.SolutionResultEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */

@Data
@TableName(value = "solutions")
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
     * 题目集ID
     */
    @TableField("pcid")
    private Integer problemCategoryId;

    /**
     * 源代码
     */
    private String sourceCode;

    /**
     * 提交时间
     * 格式化显示为 yyyy/MM/dd HH:mm:ss
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp submitTime;

    /**
     * 错误信息
     */
    private String runError;

    /**
     * 结果
     */
    private SolutionResultEnum result;
}