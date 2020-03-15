package cn.edu.jmu.system.api.problemcollection;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

/**
 * @author xeathen
 */
@Data
public class CreateProblemCollectionResponse {
    @Property(name = "id")
    private Integer id;
}
