package cn.edu.jmu.system.api.problemcategory;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xeathen
 */
@Data
public class CreateProblemCategoryRequest {
    @NotBlank
    @Property(name = "name")
    private String name;
}
