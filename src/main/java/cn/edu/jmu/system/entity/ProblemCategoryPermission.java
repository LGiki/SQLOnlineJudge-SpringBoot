package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "problem_category_permission")
public class ProblemCategoryPermission implements Serializable {
    private static final long serialVersionUID = -4707348068857964581L;

    /**
     * ID，主键，自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题目集ID
     */
    @TableField(value = "problem_category_id")
    private Integer problemCategoryId;

    /**
     * 用户组ID
     */
    @TableField(value = "user_group_id")
    private Integer userGroupId;
}
