package com.wujiajun.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wujiajun
 * @date 2023/3/10/ 20:40
 */
@Data
public class PageResult extends Result implements Serializable {
    //总记录数
    @ApiModelProperty("总记录数")
    private long total;
    //分页列表
    @ApiModelProperty(value = "分页的数据列表")
    private List<?> rows;

    public PageResult(long total, List<?> list) {
        this.setFlag(true);
        this.setMessage("分页查询成功");
        this.total = total;
        this.rows = list;
    }

    /**
     * 直接返回分页数据
     * @param total 分页总条数
     * @param list 分页数据列表
     * @return
     */
    public static Result pageResult(long total, List<?> list) {
        return new PageResult(total, list);
    }
}
