package cn.edu.jmu.sqlonlinejudge;

import cn.edu.jmu.sqlonlinejudge.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected MyUserDetailService myUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                //不需要验证的URL
                .antMatchers("/", "/api/**", "/css/**", "/js/**", "/img/**").permitAll()
                //需要特定角色的用户才能访问的URL
                .antMatchers("/admin/**").hasRole("管理员")
                //其他未指定的URL需要用户登录才能访问
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/api/login")
                //登录成功处理
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"status\":\"ok\",\"msg\":\"登录成功\"}");
                    out.flush();
                    out.close();
                })
                //登录失败处理
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"status\":\"error\",\"msg\":\"登录失败\"}");
                    out.flush();
                    out.close();
                })
                //登录提交用户名参数
                .usernameParameter("username")
                //登录提交密码参数
                .passwordParameter("password").permitAll()
                //设置注销登录成功跳转到的URL
                .and().logout().logoutSuccessUrl("/").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return EncryptUtil.md5(charSequence);
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                if (charSequence == null || s == null) {
                    return false;
                }
                return s.equals(EncryptUtil.md5(charSequence));
            }
        });
    }
}
