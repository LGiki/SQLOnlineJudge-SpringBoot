package cn.edu.jmu.security.config.admin;

import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.Permission;
import cn.edu.jmu.system.entity.Role;
import cn.edu.jmu.system.service.AdminService;
import cn.edu.jmu.system.service.PermissionService;
import cn.edu.jmu.system.service.RoleService;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sgh
 * @date 2019/8/19 下午6:14
 */
@Slf4j
public class AdminRealm extends AuthorizingRealm {

    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("AdminRealm---------------->管理员授权方法");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Admin admin = principalCollection.oneByType(Admin.class);
        if (ObjectUtils.isEmpty(admin)) {
            return info;
        }
        Role role = roleService.findRoleByAdminId(admin.getId());
        if (ObjectUtils.isEmpty(role)) {
            info.addRole("teacher");
            return info;
        }
        info.addRole(role.getRoleName());
        List<Permission> permissions = permissionService.findAllPermissionByRoleId(role.getId());
        if (!ObjectUtils.isEmpty(permissions)) {
            permissions.forEach(permission -> info.addStringPermission(permission.getPermission()));
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("AdminRealm------------------->doGetAuthenticationInfo");
        // 1. 把AuthenticationToken转换为UserToken
        UserToken userToken = (UserToken) authenticationToken;
        // 2. 从UserToken中获取username
        String username = userToken.getUsername();
        // 3. 若用户不存在，抛出UnknownAccountException异常
        Admin admin = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getUsername, username));
        if (admin == null) {
            throw new UnknownAccountException("用户不存在！");
        }
        // 4. 根据用户的情况，来构建AuthenticationInfo对象并返回，通常使用的实现类为SimpleAuthenticationInfo
        return new SimpleAuthenticationInfo(admin, admin.getPassword()
            , ByteSource.Util.bytes(admin.getUsername() + admin.getSalt()), getName());
    }
}
