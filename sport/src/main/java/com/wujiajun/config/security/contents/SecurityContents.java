package com.wujiajun.config.security.contents;

/**
 * 白名单
 * @author wujiajun
 * @date 2023/3/19/ 17:17
 */
public class SecurityContents {
    public static final String[] WHITE_LIST = {
            //后端的登陆接口
            "/user/login",
            //swagger相关
            "/favicon.ico",
            "/doc.html",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/**",
            "/configuration/ui",
            "/configuration/security",
            "/tool/forget/password",

            //小程序相关
            "/mini/login",
    };
}
