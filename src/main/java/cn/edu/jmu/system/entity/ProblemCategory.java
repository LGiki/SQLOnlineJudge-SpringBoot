package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xeathen
 */
@Data
@TableName(value = "problem_categories")
public class ProblemCategory implements Serializable {
    private static final long serialVersionUID = 7126714545199482422L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull
    @TableField(value = "name")
    protected String name;

    @NotNull
    @TableField(value = "start_time")
    private Date startTime;

    @NotNull
    @TableField(value = "end_time")
    private Date endTime;

    @NotNull
    @TableField(value = "view_after_end")
    private Boolean viewAfterEnd;
}
