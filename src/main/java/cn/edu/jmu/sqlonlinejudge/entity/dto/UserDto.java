package cn.edu.jmu.sqlonlinejudge.entity.dto;

import cn.edu.jmu.sqlonlinejudge.service.enums.UserStatusEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午3:28
 */
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -7990570472264848692L;

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 邮箱地址
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private UserStatusEnum status;
}
