package cn.edu.jmu.security.config.jwt;

import cn.edu.jmu.common.enums.LoginTypeEnum;
import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.security.util.JwtTokenUtil;
import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.service.AdminService;
import cn.edu.jmu.system.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author sgh
 * @date 2019/8/22 下午9:28
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    public JwtRealm() {
        this.setCredentialsMatcher(new JwtCredentialsMatcher());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("JwtRealm-------------------->doGetAuthorizationInfo");
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        log.debug("JwtRealm-------------------->doGetAuthenticationInfo");
        UserToken userToken = (UserToken) authenticationToken;
        String token = userToken.getToken();
        String loginType = JwtTokenUtil.getLoginTypeFromToken(token);
        String username = JwtTokenUtil.getUsernameFromToken(token);
        AuthenticationInfo authenticationInfo;
        if (LoginTypeEnum.ADMIN.getType().equals(loginType)) {
            Admin admin = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getUsername, username));
            if (admin == null) {
                throw new AuthenticationException("token过期，请重新登录");
            }
            authenticationInfo = new SimpleAuthenticationInfo(admin, admin.getSalt(), getName());
        } else {
            User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
            if (user == null) {
                throw new AuthenticationException("token过期，请重新登录");
            }
            authenticationInfo = new SimpleAuthenticationInfo(user, user.getSalt(), getName());
        }
        return authenticationInfo;
    }
}
