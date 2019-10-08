package cn.edu.jmu.system.entity.dto;

import cn.edu.jmu.system.service.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午3:28
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
     * 密码
     */
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

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

    /**
     * 提交数
     */
    private Integer submit;

    /**
     * 通过数
     */
    private Integer solved;
}
