package cn.edu.jmu.system.api;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

/**
 * @author xeathen
 */
@Data
public class CreateUserGroupResponse {
    @Property(name = "id")
    private Integer id;
}
