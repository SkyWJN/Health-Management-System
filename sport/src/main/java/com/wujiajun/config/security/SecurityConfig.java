package com.wujiajun.config.security;

import com.wujiajun.config.security.contents.SecurityContents;
import com.wujiajun.config.security.handler.JwtAccessDeniedHandler;
import com.wujiajun.config.security.handler.JwtAuthenticationEntryPoint;
import com.wujiajun.config.security.handler.JwtAuthenticationFilter;
import com.wujiajun.config.security.service.UserDetailsServiceImpl;
import com.wujiajun.entity.SysUser;
import com.wujiajun.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 权限基本配置
 *
 * @author wujiajun
 * @date 2023/3/16/ 11:19
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    /**
     * 一般用于配置白名单
     * 白名单：没有权限也可以访问的资源
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(SecurityContents.WHITE_LIST);
    }

    /**
     * Security的核心配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1. 使用jwt，首先关闭跨域攻击，否则请求头送不过来
        http.csrf().disable();
        //2. 关闭session，使用请求头带的jwt信息，不需要用到security自带的session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //3. 白名单以外的资源，请求都需要认证之后才能访问
        http.authorizeRequests().anyRequest().authenticated();
        //4. 关闭缓存，使用前端的请求头，不需要使用到缓存
        http.headers().cacheControl();
        //5. token过滤器，校验token
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //6. 没有登录、没有权限访问资源自定义返回结果
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler);
    }

    /**
     * 自定义登陆逻辑的配置
     * 也是配置到Security中进行验证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
