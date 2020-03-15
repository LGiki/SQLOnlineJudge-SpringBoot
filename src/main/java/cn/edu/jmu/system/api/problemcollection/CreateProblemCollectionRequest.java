package cn.edu.jmu.system.api.problemcollection;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

/**
 * @author xeathen
 */
@Data
public class CreateProblemCollectionRequest {
    @Property(name = "categoryId")
    private Integer categoryId;

    @Property(name = "problemId")
    private Integer problemId;
}
