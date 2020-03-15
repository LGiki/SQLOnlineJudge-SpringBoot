package cn.edu.jmu.system.api.usergourp;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xeathen
 */
@Data
public class CreateUserGroupRequest {
    @NotBlank
    @Property(name = "name")
    private String name;

    @Property(name = "description")
    private String description;
}
