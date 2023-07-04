package com.wujiajun.service;

import com.wujiajun.entity.SysRole;
import com.wujiajun.utils.QueryInfo;
import com.wujiajun.utils.Result;

/**
 * @author wujiajun
 * @date 2023/4/19/ 1:35
 */
public interface SysRoleService {
    /**
     * 分页查询
     * @param queryInfo 页码、页数大小、查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加角色数据
     * @param role
     * @return
     */
    Result insert(SysRole role);

    /**
     * 删除角色数据
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 修改角色数据
     * @param role
     * @return
     */
    Result update(SysRole role);

    Result findAll();
}
