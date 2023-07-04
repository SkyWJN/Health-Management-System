package com.wujiajun.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据权限
 * @author wujiajun
 * @date 2023/3/14/ 13:36
 */
@Data
public class SysPermission {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 数据权限标签名称
     */
    @ApiModelProperty(value = "数据权限标签名称")
    private String label;

    /**
     * 数据权限标签值
     */
    @ApiModelProperty(value = "数据权限标签值")
    private String code;

    @ApiModelProperty(value = "显示状态(0不显示、1显示)")
    private boolean status;

}
