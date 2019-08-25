package cn.edu.jmu.security.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 当配置了多个Realm时，我们通常使用的认证器是shiro自带的org.apache.shiro.authc.pam.ModularRealmAuthenticator，
 * 其中决定使用的Realm的是doAuthenticate()方法
 *
 * @author sgh
 * @date 2019/8/19 下午5:57
 */
@Slf4j
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 自定义doAuthenticator
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("UserModularRealmAuthenticator----------->doAuthenticate");
        // 强制转换回自定义的UserToken
        UserToken userToken = (UserToken) authenticationToken;
        // 获取登录类型
        String loginType = userToken.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        List<Realm> typeRealms = new ArrayList<>();
        realms.forEach(realm -> {
            log.debug("UserModularRealmAuthenticator---------------->" + realm.getName());
            if (realm.getName().contains(loginType)) {
                typeRealms.add(realm);
            }
        });
        return doMultiRealmAuthentication(typeRealms, userToken);
    }
}
