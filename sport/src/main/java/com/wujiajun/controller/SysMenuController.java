package com.wujiajun.controller;

import com.wujiajun.entity.SysMenu;
import com.wujiajun.service.SysMenuService;
import com.wujiajun.utils.QueryInfo;
import com.wujiajun.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wujiajun
 * @date 2023/4/12/ 17:57
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单数据")
public class SysMenuController {
    @Autowired
    private SysMenuService menuService;

    @ApiOperation(value = "查询所有的父级菜单")
    @GetMapping("/findParent")
    public Result findParent(){
        return menuService.findParent();
    }

    @ApiOperation(value ="分页查询菜单数据")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return menuService.findPage(queryInfo);
    }

    @ApiOperation(value = "添加菜单数据")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysMenu sysMenu){
        return menuService.insert(sysMenu);
    }

    @ApiOperation(value = "修改菜单数据")
    @PutMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu){
        return menuService.update(sysMenu);
    }

    @ApiOperation(value = "删除菜单数据")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return menuService.delete(id);
    }

}
