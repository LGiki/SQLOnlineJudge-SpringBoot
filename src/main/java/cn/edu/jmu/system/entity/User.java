package cn.edu.jmu.system.entity;

import cn.edu.jmu.system.service.enums.UserStatusEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/6/20 18:35
 */
@Data
@TableName(value = "sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 8281624782801714916L;
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank
    @TableField(value = "username")
    private String username;

    /**
     * 学号
     */
    @TableField(value = "student_no")
    private String studentNo;

    /**
     * 密码
     */
    @NotBlank
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
    @NotBlank
    @Email
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