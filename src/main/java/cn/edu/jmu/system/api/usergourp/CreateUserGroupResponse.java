package cn.edu.jmu.system.api.usergourp;

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
