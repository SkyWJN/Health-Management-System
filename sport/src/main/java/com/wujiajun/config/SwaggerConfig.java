package com.wujiajun.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口文档配置类
 *
 * @author wujiajun
 * @date 2023/3/19/ 16:49
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    /**
     * 创建接口文档
     * @return
     */
    @Bean
    public Docket creatApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wujiajun.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * 设置文档信息
     * @return
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("个人运动管理系统")
                .version("0.0.1")
                .contact(new Contact("wujiajun", "http://localhost:8080/doc.html", "1744881633@qq.com"))
                .description("个人运动管理平台的接口文档")
                .build();
    }

    /**
     * 设置请求的信息
     * @return
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> list = new ArrayList<>();
        ApiKey key = new ApiKey("Authorization", "Authorization", "Header");
        list.add(key);
        return list;
    }

    /**
     * 配置security对swagger测试的权限
     * @return
     */
    public List<SecurityContext> securityContexts(){
        List<SecurityContext> list = new ArrayList<>();
        list.add(getSecurityContext());
        return list;
    }

    /**
     * 得到授权路径
     * @return
     */
    private SecurityContext getSecurityContext(){
        return SecurityContext
                .builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("hello/.*"))
                .build();
    }

    /**
     * 授权给swagger，可以进行接口测试
     * @return
     */
    private List<SecurityReference> securityReferences(){
        List<SecurityReference> list = new ArrayList<>();
        //授权范围 全局
        AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = scope;
        list.add(new SecurityReference("Authorization",scopes));
        return list;
    }


}
