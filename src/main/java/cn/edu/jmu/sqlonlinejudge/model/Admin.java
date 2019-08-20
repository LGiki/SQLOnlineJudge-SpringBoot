package cn.edu.jmu.sqlonlinejudge.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sgh
 * @date 2019-08-19
 */
@Data
@TableName("sys_admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 836329461151087062L;

    @TableId
    private Integer id;

    private String username;

    private String name;

    private String password;

    private String salt;


}
