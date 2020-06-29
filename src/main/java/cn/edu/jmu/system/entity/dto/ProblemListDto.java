package cn.edu.jmu.system.entity.dto;

import cn.edu.jmu.system.entity.Problem;
import lombok.Data;

import java.io.Serializable;

/**
 * 后台管理题目列表Dto
 */
@Data
public class ProblemListDto implements Serializable {

    private static final long serialVersionUID = 4380346650079743142L;


    public ProblemListDto(Problem problem) {
        this.problemId = problem.getId();
        this.problemTitle = problem.getTitle();
        this.problemDifficulty = problem.getDifficulty();
    }

    /**
     * 题目ID
     */
    private Integer problemId;

    /**
     * 题目标题
     */
    private String problemTitle;

    /**
     * 题目难度
     */
    private Integer problemDifficulty;

    /**
     * 数据库ID
     */
    private Integer databaseId;

    /**
     * 数据库名称
     */
    private String databaseName;
}
