package cn.edu.jmu.system.api.problemcategory;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

/**
 * @author xeathen
 */
@Data
public class DeleteProblemCategoryResponse {
    @Property(name = "id")
    private Integer id;
}
