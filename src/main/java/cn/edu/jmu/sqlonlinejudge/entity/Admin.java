package cn.edu.jmu.sqlonlinejudge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019-08-19
 */
@Data
@TableName("sys_admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 836329461151087062L;

    /**
     * 管理员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员用户名
     */
    @NotBlank
    @TableField(value = "username")
    private String username;

    /**
     * 管理员密码
     */
    @NotBlank
    @TableField(value = "password")
    private String password;

    /**
     * 密码盐
     */
    @TableField(value = "salt")
    private String salt;
}
