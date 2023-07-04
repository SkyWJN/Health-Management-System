package com.wujiajun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wujiajun.entity.SysMenu;
import com.wujiajun.entity.SysPermission;
import com.wujiajun.entity.SysRole;
import com.wujiajun.mapper.SysMenuMapper;
import com.wujiajun.mapper.SysPermissionMapper;
import com.wujiajun.mapper.SysRoleMapper;
import com.wujiajun.service.SysRoleService;
import com.wujiajun.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wujiajun
 * @date 2023/4/19/ 1:36
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("分页: 页码 -->{}， 页数大小--> {}", queryInfo.getPageNumber(), queryInfo.getPageSize());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        log.info("查询-->{}", queryInfo.getQueryString());
        Page<SysRole> page = sysRoleMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysRole> result = page.getResult();
        result.forEach(item -> {
            // 查询角色菜单信息
            List<SysMenu> menus = menuMapper.findByRoleId(item.getId());
            menus.forEach(menu -> {
                List<SysMenu> children = menuMapper.findByRoleIdAndParentId(menu.getId(), item.getId());
                menu.setChildren(children);
            });
            item.setMenus(menus);
            // 查询角色权限信息
            List<SysPermission> permissions = permissionMapper.findByRoleId(item.getId());
            item.setPermissions(permissions);
        });
        return PageResult.pageResult(total, result);
    }

    @Transactional
    @Override
    public Result insert(SysRole role) {
        log.info("查询角色信息是否存在");
        SysRole role1 = sysRoleMapper.findByLabel(role.getLabel());
        if (null != role1) {
            return Result.fail("该角色已存在");
        }
        //插入角色信息
        sysRoleMapper.insert(role);
        if (role.getPermissions().size() > 0) {
            log.info("添加角色对应的权限数据");
            role.getPermissions().forEach(item -> sysRoleMapper.insertPermissions(role.getId(), item.getId()));
        }
        if (role.getMenus().size() > 0) {
            log.info("添加角色对应的菜单数据");
            role.getMenus().forEach(item -> sysRoleMapper.insertMenus(role.getId(), item.getId()));
        }
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("添加角色信息成功！");
    }

    @Override
    public Result delete(Long id) {
        log.info("查询角色信息下是否有菜单权限");
        List<SysMenu> menus = menuMapper.findByRoleId(id);
        List<SysMenu> children = new ArrayList<>();
        menus.forEach(item -> {
            children.addAll(menuMapper.findByRoleIdAndParentId(item.getId(), id));
        });
        if (menus.size() > 0 || children.size() > 0) {
            return Result.fail("删除失败，该角色下拥有菜单信息，请先删除对应的菜单信息!");
        }
        if (permissionMapper.findByRoleId(id).size() > 0) {
            return Result.fail("删除失败，该角色下拥有权限信息，请先删除对应的权限信息!");
        }
        sysRoleMapper.delete(id);
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("删除成功！");
    }

    @Transactional
    @Override
    public Result update(SysRole role) {
        sysRoleMapper.update(role);
        if (role.getPermissions().size() >= 0) {
            log.info("先删除对应的权限数据");
            sysRoleMapper.deletePermissionById(role.getId());
            log.info("再添加对应的权限数据");
            role.getPermissions().forEach(item -> sysRoleMapper.insertPermissions(role.getId(), item.getId()));
        }
        if (role.getMenus().size() >= 0) {
            log.info("先删除对应的菜单数据");
            sysRoleMapper.deleteMenuById(role.getId());
            log.info("再添加对应的菜单数据");
            role.getMenus().forEach(item-> sysRoleMapper.insertMenus(role.getId(), item.getId()));
        }
        redisUtil.delKey("userInfo_" + SecurityUtil.getUserName());
        return Result.success("修改信息成功");
    }

    @Override
    public Result findAll() {
        return Result.success("查询所有角色成功",sysRoleMapper.findAll());
    }
}
