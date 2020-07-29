package cn.edu.jmu.system.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProblemCategoryPermissionDto implements Serializable {
    private static final long serialVersionUID = 4235844981082772964L;
    /**
     * ProblemCategoryPermission ID
     */
    private Integer id;

    /**
     * 用户组ID
     */
    private Integer userGroupId;

    /**
     * 用户组名称
     */
    private String userGroupName;
}
