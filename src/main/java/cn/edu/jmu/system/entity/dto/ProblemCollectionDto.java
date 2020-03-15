package cn.edu.jmu.system.entity.dto;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xeathen
 */
@Data
public class ProblemCollectionDto implements Serializable {
    private static final long serialVersionUID = -8161526372054994366L;

    @Property(name = "id")
    private Integer id;

    @Property(name = "categoryId")
    private Integer categoryId;

    @Property(name = "categoryName")
    private String categoryName;

    @Property(name = "problemId")
    private Integer problemId;

    @Property(name = "problemTitle")
    private String problemTitle;

    @Property(name = "problemDescription")
    private String problemDescription;

    @Property(name = "databaseId")
    private Integer databaseId;

    @Property(name = "databaseName")
    private String databaseName;
}
