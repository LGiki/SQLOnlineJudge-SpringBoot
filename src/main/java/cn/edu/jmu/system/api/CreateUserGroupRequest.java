package cn.edu.jmu.system.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xeathen
 */
@Data
public class CreateUserGroupRequest {
    /**
     * name
     */
    @NotBlank
    private String name;

    /**
     * description
     */
    private String description;
}
