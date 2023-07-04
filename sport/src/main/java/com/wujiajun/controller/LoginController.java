package com.wujiajun.controller;

import com.wujiajun.service.SysUserService;
import com.wujiajun.utils.RedisUtil;
import com.wujiajun.utils.Result;
import com.wujiajun.utils.SecurityUtil;
import com.wujiajun.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import netscape.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 登陆和退出
 * 获取当前用户的基本信息
 * @author wujiajun
 * @date 2023/3/14/ 10:56
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户使用接口")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "登陆接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        return sysUserService.login(loginVo);
    }

    @ApiOperation(value = "获取用户基本信息")
    @GetMapping("/getInfo")
    public Result getUserInfo(){
        return Result.success("获取用户信息成！", SecurityUtil.getUser());
    }

    @ApiOperation(value = "用户退出登陆")
    @GetMapping("/logout")
    public Result logout(){
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return Result.success("退出成功");
    }

}
