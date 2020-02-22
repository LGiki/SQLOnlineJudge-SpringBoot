package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author sgh
 * @since 2019-08-21
 */
@Data
@TableName(value = "sys_roles")
public class Role implements Serializable {

    private static final long serialVersionUID = -7771416957946342884L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank
    @TableField(value = "role_name")
    private String roleName;

    @TableField(value = "description")
    private String description;
}
