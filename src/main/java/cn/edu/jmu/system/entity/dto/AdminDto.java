package cn.edu.jmu.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午6:17
 */
@Data
public class AdminDto implements Serializable {

    private static final long serialVersionUID = -4540020501792838091L;

    /**
     * 管理员id
     */
    private Integer id;

    /**
     * 管理员用户名
     */
    @NotBlank(message = "username不能为空")
    private String username;

    /**
     * 管理员密码
     */
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
