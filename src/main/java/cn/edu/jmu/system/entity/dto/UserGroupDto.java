package cn.edu.jmu.system.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ethan
 */
@Data
public class UserGroupDto implements Serializable {
    private static final long serialVersionUID = -527652192732855390L;

    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    private String description;
}
