package cn.edu.jmu.security.config.user;

import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.enums.UserStatusEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * @author sgh
 * @date 2019/8/19 下午3:09
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("UserRealm------------------->doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = principalCollection.oneByType(User.class);
        if (user == null) {
            return info;
        }
        if (user.getStatus().equals(UserStatusEnum.NORMAL)) {
            info.addRole("user");
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("UserRealm---------------->doGetAuthenticationInfo");
        // 1. 把AuthenticationToken转换为UserToken
        UserToken userToken = (UserToken) authenticationToken;
        // 2. 从UserToken中获取username
        String username = userToken.getUsername();
        // 3. 若用户不存在，抛出UnknownAccountException异常
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (user == null) {
            throw new UnknownAccountException("用户不存在！");
        }
        if (user.getStatus() == UserStatusEnum.LOCK) {
            throw new AuthenticationException("该用户账号被锁定！");
        }
        // 4. 根据用户的情况，来构建AuthenticationInfo对象并返回，通常使用的实现类为SimpleAuthenticationInfo
        return new SimpleAuthenticationInfo(user, user.getPassword()
            , ByteSource.Util.bytes(user.getUsername() + user.getSalt()), getName());
    }
}
