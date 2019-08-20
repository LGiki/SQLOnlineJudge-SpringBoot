package cn.edu.jmu.sqlonlinejudge.filter;

import cn.edu.jmu.sqlonlinejudge.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sgh
 * @date 2019/8/20 下午2:27
 */
@Slf4j
public class JwtFilter extends FormAuthenticationFilter {

    @Resource
    JwtTokenUtil jwtTokenUtil;


    /**
     * 判断token是否为空、过期
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) {
        // 登录接口放行
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return true;
            }
        }
        String token = getRequestToken((HttpServletRequest) request);
        if (!jwtTokenUtil.validateToken(token)) {
            log.debug("无效toke");
            throw new AuthenticationException("无效toke");
        }
        return true;
    }

    /**
     * 上面的方法如果返回false,则接下来会执行这个方法,如果返回为true,则不会执行这个方法
     * 判断是否为登录url,进一步判断请求是不是post
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            return isLoginSubmission(request, response);
        }
        return false;
    }

    /**
     * 获取请求中的token,首先从请求头中获取,如果没有,则尝试从请求参数中获取
     */
    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader(jwtTokenUtil.getHeader());
        if (!StringUtils.isBlank(token) && token.startsWith(jwtTokenUtil.getBearer())) {
            token = token.substring(jwtTokenUtil.getBearer().length());
            log.debug("获取token为:{}", token);
            return token;
        } else {
            log.debug("无效的token");
            return null;
        }
    }
}
