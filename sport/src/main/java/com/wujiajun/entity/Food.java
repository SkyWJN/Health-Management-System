package com.wujiajun.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 食物详情类
 *  Excel: name:列名   type: 导出类型 1 是文本 2 是图片,3 是函数,10 是数字 默认是文本
 *
 * @author wujiajun
 * @date 2023/5/5/ 15:13
 */
@Data
public class Food {
    @ApiModelProperty("主键")
    private Long id;

    @Excel(name = "食物名称")
    @ApiModelProperty("食物名称")
    private String title;

    @ApiModelProperty("食物类别")
    private Long typeId;

    @ApiModelProperty("图片地址")
    @Excel(name = "食物图片", type = 2)
    private String imageUrls;

    @Excel(name = "营养元素")
    @ApiModelProperty("营养元素")
    private String nutrient;

    @Excel(name = "热量")
    @ApiModelProperty("热量")
    private Float heat;

    @Excel(name = "蛋白质")
    @ApiModelProperty("蛋白质")
    private Float protein;

    @Excel(name = "脂肪")
    @ApiModelProperty("脂肪")
    private Float fat;

    @Excel(name = "碳水化合物")
    @ApiModelProperty("碳水化合物")
    private Float carbonWater;

    @Excel(name = "膳食纤维")
    @ApiModelProperty("膳食纤维")
    private Float dietaryFiber;

    @Excel(name = "维生素A")
    @ApiModelProperty("维生素A")
    private Float vitaminA;

    @Excel(name = "维生素C")
    @ApiModelProperty("维生素C")
    private Float vitaminC;

    @Excel(name = "维生素E")
    @ApiModelProperty("维生素E")
    private Float vitaminE;

    @Excel(name = "胡萝卜素")
    @ApiModelProperty("胡萝卜素")
    private Float carrot;

    @Excel(name = "维生素B1")
    @ApiModelProperty("维生素B1")
    private Float vitaminB1;

    @Excel(name = "维生素B2")
    @ApiModelProperty("维生素B2")
    private Float vitaminB2;

    @Excel(name = "烟酸")
    @ApiModelProperty("烟酸")
    private Float niacin;

    @Excel(name = "胆固醇")
    @ApiModelProperty("胆固醇")
    private Float cholesterol;

    @Excel(name = "镁")
    @ApiModelProperty("镁")
    private Float magnesium;

    @Excel(name = "铁")
    @ApiModelProperty("铁")
    private Float iron;

    @Excel(name = "钙")
    @ApiModelProperty("钙")
    private Float calcium;

    @Excel(name = "锌")
    @ApiModelProperty("锌")
    private Float zinc;

    @Excel(name = "铜")
    @ApiModelProperty("铜")
    private Float copper;

    @Excel(name = "锰")
    @ApiModelProperty("锰")
    private Float manganese;

    @Excel(name = "钾")
    @ApiModelProperty("钾")
    private Float potassium;

    @Excel(name = "磷")
    @ApiModelProperty("磷")
    private Float phosphorus;

    @Excel(name = "钠")
    @ApiModelProperty("钠")
    private Float sodium;

    @Excel(name = "硒")
    @ApiModelProperty("硒")
    private Float selenium;

    @JsonIgnore
    @Excel(name = "食物类别")
    private String typeTitle;
}
