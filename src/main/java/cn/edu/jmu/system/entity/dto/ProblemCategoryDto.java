package cn.edu.jmu.system.entity.dto;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xeathen
 */
@Data
public class ProblemCategoryDto implements Serializable {
    private static final long serialVersionUID = 7126714545199482422L;

    @Property(name = "id")
    private Integer id;

    @NotBlank
    @Property(name = "name")
    protected String name;

    @Property(name = "startTime")
    private Date startTime;

    @Property(name = "endTime")
    private Date endTime;

    @Property(name = "viewAfterEnd")
    private Boolean viewAfterEnd;
}
