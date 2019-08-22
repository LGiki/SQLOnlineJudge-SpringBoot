package cn.edu.jmu.security.config.admin;

import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.sqlonlinejudge.entity.Admin;
import cn.edu.jmu.sqlonlinejudge.entity.Permission;
import cn.edu.jmu.sqlonlinejudge.entity.Role;
import cn.edu.jmu.sqlonlinejudge.service.AdminService;
import cn.edu.jmu.sqlonlinejudge.service.PermissionService;
import cn.edu.jmu.sqlonlinejudge.service.RoleService;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
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
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
        List<Role> roles = roleService.findAllRoleByAdminId(admin.getId());
        if (ObjectUtils.isEmpty(roles)) {
            return info;
        }
        roles.forEach(role -> {
            info.addRole(role.getRoleName());
            List<Permission> permissions = permissionService.findAllPermissionByRoleId(role.getId());
            if (!ObjectUtils.isEmpty(permissions)) {
                permissions.forEach(permission -> info.addStringPermission(permission.getPermission()));
            }
        });
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
        return new SimpleAuthenticationInfo(admin, admin.getPassword(), getName());
    }
}
