package cn.edu.jmu.system.api.usergourp;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import java.util.List;

/**
 * @author xeathen
 */
@Data
public class SearchUserGroupResponse {
    @Property(name = "userGroups")
    private List<UserGroup> userGroups;

    @Property(name = "total")
    private Long total;

    @Data
    public static class UserGroup {
        @Property(name = "id")
        private Integer id;

        @Property(name = "name")
        private String name;

        @Property(name = "description")
        private String description;
    }
}
