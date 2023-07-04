package com.wujiajun.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域处理
 * @author wujiajun
 * @date 2023/3/10/ 20:17
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")                      //允许访问路径
                .allowedOrigins("http://localhost:8081",null)       //配置请求来源
                .allowedMethods("GET","POST","DELETE","PUT","OPTION")   //允许跨域访问的方法
                .allowCredentials(true)                             //允许携带参数
                .allowedHeaders()                                   //允许请求头
                .maxAge(3600);                                      //最大响应时间
    }
}
