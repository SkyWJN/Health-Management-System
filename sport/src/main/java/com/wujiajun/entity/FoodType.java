package com.wujiajun.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wujiajun
 * @date 2023/5/5/ 15:13
 */
@Data
public class FoodType {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("分类标题")
    private String title;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("分类下的食物")
    private List<Food> foods;
}
