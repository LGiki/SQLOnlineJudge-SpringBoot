package cn.edu.jmu.system.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserGroupCollectionDto implements Serializable {
    private static final long serialVersionUID = -1445767324956204237L;

    /**
     * 用户组关系ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户组ID
     */
    private Integer userGroupId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户学号
     */
    private String studentNo;
}
