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

    /**
     * 用户组ID
     */
    @Property(name = "id")
    private Integer id;

    /**
     * 用户组名称
     */
    @Property(name = "name")
    private String name;

    /**
     * 用户组内的用户数
     */
    private Integer count;
}
