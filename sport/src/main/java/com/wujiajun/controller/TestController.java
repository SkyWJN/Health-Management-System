package com.wujiajun.controller;

import com.wujiajun.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wujiajun
 * @date 2023/3/10/ 13:31
 */
@RestController
@Api(value = "测试接口")
public class TestController {

    @ApiOperation(value = "测试")
    @PreAuthorize("hasAnyAuthority('PRE_INSERT')")
    @GetMapping("/test")
    public Result Test(){
        return Result.success("信息返回成功","你好！");
    }

}