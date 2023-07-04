package com.wujiajun.utils;

import lombok.Data;

/**
 * @author wujiajun
 * @date 2023/3/10/ 21:08
 */
@Data
public class QueryInfo {
    //第几页
    private Integer pageNumber;

    //一页多少条数据
    private Integer pageSize;

    //查询内容
    private String queryString;
}
