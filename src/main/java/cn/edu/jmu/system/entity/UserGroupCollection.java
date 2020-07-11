package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName(value = "user_group_collections")
public class UserGroupCollection implements Serializable {

    private static final long serialVersionUID = 4835202737556377304L;

    /**
     * 用户组关系ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 用户组ID
     */
    @TableField(value = "user_group_id")
    private Integer userGroupId;
}
