package com.wujiajun.service;

import com.wujiajun.entity.Sport;
import com.wujiajun.entity.WxRun;
import com.wujiajun.utils.QueryInfo;
import com.wujiajun.utils.Result;

import java.util.List;

/**
 * @author wujiajun
 * @date 2023/5/8/ 16:57
 */
public interface SportService {

    /**
     * 删除菜品
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 获取详情
     * @param id
     * @return
     */
    Result findInfo(Long id);

    /**
     * 修改菜品
     * @param sport
     * @return
     */
    Result update(Sport sport);

    /**
     * 添加菜品
     * @param sport
     * @return
     */
    Result insert(Sport sport);

    /**
     * 分页查询菜品信息
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    Result insertStep(List<WxRun> runs);

    Result stepReport();


}
