package com.wujiajun.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wujiajun
 * @date 2023/3/14/ 14:10
 */
@Data
@ApiModel(value = "登陆参数")
public class LoginVo {
    /**
     * 登陆参数
     */
    @ApiModelProperty(value = "用户名",dataType = "String")
    private String username;
    @ApiModelProperty(value = "密码",dataType = "String")
    private String password;
}
