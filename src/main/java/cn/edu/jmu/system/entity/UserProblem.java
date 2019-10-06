package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xeathen
 * @date 2019/10/5 10:25
 */
@Data
@TableName(value = "user_problem")
public class UserProblem implements Serializable {

    private static final long serialVersionUID = -3270267373811937646L;

    /**
     * 题目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    /**
     * 题目ID
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     *
     */
    @TableId(value = "state", type = IdType.AUTO)
    private String state;

}
