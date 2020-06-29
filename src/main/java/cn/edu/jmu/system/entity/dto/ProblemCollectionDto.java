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

    /**
     * 题目集 ID
     */
    @Property(name = "categoryId")
    private Integer categoryId;

//    /**
//     * 题目集名称
//     */
//    @Property(name = "categoryName")
//    private String categoryName;

    /**
     * 题目 ID
     */
    @Property(name = "problemId")
    private Integer problemId;

    /**
     * 题目标题
     */
    @Property(name = "problemTitle")
    private String problemTitle;

    /**
     * 题目难度
     */
    @Property(name = "problemDifficulty")
    private Integer problemDifficulty;

    /**
     * 题目通过数
     */
    @Property(name = "problemSolved")
    private Integer problemSolved;

    /**
     * 题目提交数
     */
    @Property(name = "problemSubmit")
    private Integer problemSubmit;

    /**
     * 数据库 ID
     */
    @Property(name = "databaseId")
    private Integer databaseId;

    /**
     * 数据库名称
     */
    @Property(name = "databaseName")
    private String databaseName;

    /**
     * 题目的分值
     */
    @Property(name = "problemScore")
    private Integer problemScore;
}
