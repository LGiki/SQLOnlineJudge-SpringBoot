package cn.edu.jmu.system.api;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

/**
 * @author ethan
 */
@Data
public class DeleteUserGroupResponse {
    @Property(name = "id")
    private Integer id;
}
