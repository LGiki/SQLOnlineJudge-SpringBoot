package cn.edu.jmu.sqlonlinejudge.entity;

import cn.edu.jmu.sqlonlinejudge.service.enums.UserStatusEnum;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/6/20 18:35
 */
@Data
@TableName(value = "sys_user")
public class User implements Serializable {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 密码盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 邮箱地址
     */
    @TableField(value = "email")
    private String email;


    /**
     * 提交数
     */
    @TableField(value = "submit")
    private Integer submit;

    /**
     * 通过数
     */
    @TableField(value = "solved")
    private Integer solved;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 状态
     */
    @EnumValue
    @TableField(value = "status")
    private UserStatusEnum status;
}