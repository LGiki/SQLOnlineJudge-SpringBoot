package cn.edu.jmu.security.filter;

import cn.edu.jmu.security.config.UserToken;
import cn.edu.jmu.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sgh
 * @date 2019/8/20 下午2:27
 */
@Slf4j
public class JwtFilter extends AuthenticatingFilter {

    /**
     * 判断token是否为空、过期
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws UnauthorizedException {
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e) { //not found any token
            log.error("Not found any token");
        } catch (Exception e) {
            log.error("Error occurs when login", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    /**
     * 如果这个Filter在之前isAccessAllowed（）方法中返回false,则会进入这个方法。我们这里直接返回错误的response
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.debug("hello");
        return false;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = ((HttpServletRequest) servletRequest).getHeader(JwtTokenUtil.header);
        // 判断请求头是否带上了token
        if (!StringUtils.isBlank(token) && token.startsWith(JwtTokenUtil.bearer)) {
            token = token.substring(JwtTokenUtil.bearer.length());
            log.debug("JwtFilter--------------->createToken-------------->" + token);
            if (JwtTokenUtil.validateToken(token)) {
                return new UserToken(token, "Jwt");
            }
        }
        return null;
    }
}
