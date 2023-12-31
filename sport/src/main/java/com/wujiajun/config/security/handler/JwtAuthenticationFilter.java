package com.wujiajun.config.security.handler;

import com.wujiajun.config.security.service.UserDetailsServiceImpl;
import com.wujiajun.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token认证
 * 在接口访问前进行过滤
 *
 * @author wujiajun
 * @date 2023/3/16/ 16:59
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 请求前获取请求头信息token
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 获取token
        String header = request.getHeader(tokenHeader);
        //2. 判断token是否存在
        if (null != header && header.startsWith(tokenHead)) {
            //拿到token主体
            String token = header.substring(tokenHead.length());
            //根据token获取用户名
            String username = tokenUtil.getUsernameByToken(token);
            //3. token存在但是没有登陆信息
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                //么有登陆信息，直接登陆
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //判断token是否有效
                if (!tokenUtil.isExpiration(token) && username.equals(userDetails.getUsername())) {
                    //刷新security中的用户信息
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        //过滤器放行
        filterChain.doFilter(request,response);
    }
}
