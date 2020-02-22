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
@TableName(value = "sys_permissions")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1281933827608139147L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @NotBlank
    @TableField(value = "permission")
    private String permission;
}
