package com.wujiajun.utils;

import com.wujiajun.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;
import springfox.documentation.spi.service.contexts.SecurityContext;

/**
 * 用于获取用户登陆的基本信息
 * @author wujiajun
 * @date 2023/3/20/ 15:55
 */
public class SecurityUtil {

    /**
     * 从Security主体信息中获取用户信息
     * @return
     */
    public static SysUser getUser(){
        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
        user.setName(user.getUsername());
        return user;
    }

    /**
     * 在security中获取用户名
     * @return
     */
    public static String getUserName(){
        return getUser().getUsername();
    }

    /**
     * 在security中获取用户ID
     * @return
     */
    public static Long gerUserId(){
        return getUser().getId();
    }

    /**
     * 在security中获取用户小程序ID
     * @return
     */
    public static String getOpenId() {
        return getUser().getOpenId();
    }

}
