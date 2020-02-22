package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xeathen
 */
@Data
@TableName(value = "sys_user_groups")
public class UserGroup implements Serializable {
    private static final long serialVersionUID = 5404632345151826499L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * name
     */
    @NotBlank
    @TableField(value = "name")
    private String name;

    /**
     * description
     */
    @TableField(value = "description")
    private String description;
}
