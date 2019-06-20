package cn.edu.jmu.sqlonlinejudge.model;

import cn.edu.jmu.sqlonlinejudge.model.enums.UserRole;
import cn.edu.jmu.sqlonlinejudge.model.enums.UserStatus;
import lombok.Data;

/**
 * @author sgh
 * @date 2019/6/20 18:35
 */
@Data
public class User {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 邮箱是否有效
     */
    private Boolean isEmailValid;

    /**
     * 提交数
     */
    private Integer submit;

    /**
     * 通过数
     */
    private Integer solved;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private UserStatus status;

    /**
     * 用户角色
     */
    private UserRole role;
}