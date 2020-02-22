package cn.edu.jmu.system.api;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

/**
 * @author ethan
 */
@Data
public class UpdateUserGroupRequest {
    @Property(name = "name")
    private String name;

    @Property(name = "description")
    private String description;
}
