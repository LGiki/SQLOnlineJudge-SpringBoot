package cn.edu.jmu.system.entity.dto;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xeathen
 */
@Data
public class UserGroupDto implements Serializable {
    private static final long serialVersionUID = -527652192732855390L;

    @Property(name = "id")
    private Integer id;

    @Property(name = "name")
    private String name;

    @Property(name = "description")
    private String description;
}
