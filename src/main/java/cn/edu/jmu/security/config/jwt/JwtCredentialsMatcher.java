package cn.edu.jmu.security.config.jwt;

import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @author sgh
 */
@Slf4j
public class JwtCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        UserToken userToken = (UserToken) authenticationToken;
        String token = userToken.getToken();
        try {
            return JwtTokenUtil.validateToken(token);
        } catch (Exception e) {
            log.error("Token Error:{}", e.getMessage());
        }
        return false;
    }
}
