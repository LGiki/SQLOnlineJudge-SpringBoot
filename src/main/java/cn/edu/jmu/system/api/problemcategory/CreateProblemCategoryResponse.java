package cn.edu.jmu.system.api.problemcategory;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

/**
 * @author xeathen
 */
@Data
public class CreateProblemCategoryResponse {
    @Property(name = "id")
    private Integer id;
}
