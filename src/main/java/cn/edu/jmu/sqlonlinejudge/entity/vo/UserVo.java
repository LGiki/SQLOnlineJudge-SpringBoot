package cn.edu.jmu.sqlonlinejudge.entity.vo;

import cn.edu.jmu.sqlonlinejudge.service.enums.UserStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sgh
 * @date 2019/8/26 下午3:24
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = -1860981309589191032L;

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 提交数
     */
    private Integer submit;

    /**
     * 通过数
     */
    private Integer solved;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private UserStatusEnum status;
}
