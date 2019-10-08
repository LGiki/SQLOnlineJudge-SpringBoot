package cn.edu.jmu.system.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author xeathen
 * @date 2019/10/5 10:28
 */
public class UserProblemDto implements Serializable {

    private static final long serialVersionUID = -4224603356254738248L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField(value = "uid")
    private Integer uid;

    /**
     * 题目ID
     */
    @TableField(value = "pid")
    private Integer pid;

    /**
     * 状态
     */
    @TableField(value = "state")
    private String state;
}
