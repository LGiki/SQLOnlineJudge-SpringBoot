package cn.edu.jmu.system.entity.dto;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xeathen
 */
@Data
public class ProblemCategoryDto implements Serializable {
    private static final long serialVersionUID = 7126714545199482422L;

    @Property(name = "id")
    private Integer id;

    @NotBlank
    @Property(name = "name")
    protected String name;

    @Property(name = "startTime")
    private Date startTime;

    @Property(name = "endTime")
    private Date endTime;

    @Property(name = "viewAfterEnd")
    private Boolean viewAfterEnd;

    /**
     * 题目集状态：未开始、正在进行、已结束（分为结束后能查看题目、结束后不能查看题目）、未知
     */
    private String status;
}
