package cn.edu.jmu.system.api.problemcollection;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

@Data
public class UpdateProblemScoreRequest {
    @Property(name = "newProblemScore")
    private Integer newProblemScore;
}
