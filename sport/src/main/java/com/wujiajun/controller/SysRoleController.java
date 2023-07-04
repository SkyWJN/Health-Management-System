package com.wujiajun.controller;

import com.wujiajun.entity.SysRole;
import com.wujiajun.service.SysRoleService;
import com.wujiajun.utils.QueryInfo;
import com.wujiajun.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wujiajun
 * @date 2023/4/19/ 2:41
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return sysRoleService.findPage(queryInfo);
    }

    @ApiOperation(value = "添加用户时角色信息列表")
    @GetMapping("/findAll")
    public Result findAll(){
        return sysRoleService.findAll();
    }

    @ApiOperation(value = "添加角色信息")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysRole sysRole){
        return sysRoleService.insert(sysRole);
    }

    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return sysRoleService.delete(id);
    }

    @ApiOperation(value = "修改角色信息")
    @PutMapping("/update")
    public Result update(@RequestBody SysRole sysRole){
        return sysRoleService.update(sysRole);
    }

}
