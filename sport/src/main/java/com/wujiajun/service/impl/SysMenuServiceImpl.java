package com.wujiajun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wujiajun.entity.SysMenu;
import com.wujiajun.mapper.SysMenuMapper;
import com.wujiajun.service.SysMenuService;
import com.wujiajun.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wujiajun
 * @date 2023/4/12/ 17:43
 */
@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result findParent() {
        List<SysMenu> parent = sysMenuMapper.findParent();
        return Result.success("查询父级菜单成功",parent);
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始数据分页-->页码{},-->页数{},-->查询内容{}",queryInfo.getPageNumber(),queryInfo.getPageSize(),queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<SysMenu> page = sysMenuMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysMenu> result = page.getResult();
        log.info("查询的总条数-->{}", total);
        log.info("分页列表-->{}", result);
        return new PageResult(total,result);
    }

    @Override
    public Result insert(SysMenu menu) {
        sysMenuMapper.insert(menu);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("菜单数据添加成功");
    }

    @Override
    public Result delete(Long id) {
        sysMenuMapper.delete(id);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("删除菜单数据成功");
    }

    @Override
    public Result update(SysMenu menu) {
        sysMenuMapper.update(menu);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("修改菜单数据成功");
    }
}
