package cn.edu.jmu.security.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author sgh
 * @date 2019/8/19 下午4:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserToken extends UsernamePasswordToken {

    private static final long serialVersionUID = -4230076326978280598L;
    /**
     * token
     */

    private String token;

    /**
     * 登录类型
     */
    private String loginType;

    public UserToken(String token, String loginType) {
        this.token = token;
        this.loginType = loginType;
    }

    public UserToken(String username, String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
}
