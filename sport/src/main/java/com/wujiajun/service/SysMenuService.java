package com.wujiajun.service;

import com.wujiajun.entity.SysMenu;
import com.wujiajun.utils.QueryInfo;
import com.wujiajun.utils.Result;

/**
 * @author wujiajun
 * @date 2023/4/12/ 17:42
 */
public interface SysMenuService {
    /**
     * 查询所有父级菜单
     * @return
     */
    Result findParent();

    /**
     * 分页查询
     * @param queryInfo 页码、页数大小、查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加菜单数据
     * @param menu
     * @return
     */
    Result insert(SysMenu menu);

    /**
     * 删除菜单数据
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 修改菜单数据
     * @param menu
     * @return
     */
    Result update(SysMenu menu);
}
