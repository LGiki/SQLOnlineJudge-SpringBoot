package cn.edu.jmu.system.api.problemcategory;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author xeathen
 */
@Data
public class CreateProblemCategoryRequest {
    @NotBlank
    @Property(name = "name")
    private String name;

    @Property(name = "startTime")
    private Date startTime;

    @Property(name = "endTime")
    private Date endTime;

    @Property(name = "viewAfterEnd")
    private Boolean viewAfterEnd;
}
