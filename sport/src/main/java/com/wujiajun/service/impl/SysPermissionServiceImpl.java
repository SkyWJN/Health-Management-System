package com.wujiajun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wujiajun.entity.SysPermission;
import com.wujiajun.mapper.SysPermissionMapper;
import com.wujiajun.service.SysPermissionService;
import com.wujiajun.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wujiajun
 * @date 2023/4/3/ 15:06
 */
@Service
@Slf4j
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始权限数据分页-->页码{}, --->{}页数--->查询内容{}", queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(),queryInfo.getPageSize());
        Page<SysPermission> page = sysPermissionMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysPermission> result = page.getResult();
        log.info("查询的总条数-->{}", total);
        log.info("分页列表-->{}", result);
        return new PageResult(total,result);
    }

    @Override
    public Result insert(SysPermission permission) {
        sysPermissionMapper.insert(permission);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("添加权限数据成功");
    }

    @Override
    public Result delete(Long id) {
        sysPermissionMapper.delete(id);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("删除权限数据成功");
    }

    @Override
    public Result update(SysPermission permission) {
        sysPermissionMapper.update(permission);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("修改权限数据成功");
    }

    @Override
    public Result findAll() {
        List<SysPermission> list = sysPermissionMapper.findAll();
        return Result.success("查询权限信息成功",list);
    }
}
