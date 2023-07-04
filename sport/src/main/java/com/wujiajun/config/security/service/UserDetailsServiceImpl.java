package com.wujiajun.config.security.service;

import com.wujiajun.entity.SysMenu;
import com.wujiajun.entity.SysRole;
import com.wujiajun.entity.SysUser;
import com.wujiajun.mapper.SysUserMapper;
import com.wujiajun.utils.RedisUtil;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现UseDetailsService接口，实现自定义登陆逻辑
 * 重写loadUserByUsername方法
 *
 * @author wujiajun
 * @date 2023/3/16/ 17:59
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user;
        if (redisUtil.haskey("userInfo_" + username)) {
            //从redis中获取用户信息
            user = (SysUser) redisUtil.getValue("userInfo_" + username);
            redisUtil.expire("userInfo_" + username,5);
        } else {
            //根据用户名获取用户信息
            user = userMapper.findByUsername(username);
            if (null == user) {
                throw new UsernameNotFoundException("用户名或密码错误！");
            }
            if (user.isAdmin()) {
                //管理员
                user.setRoles(userMapper.findRoles(null));
                user.setPermissions(userMapper.findPermissions(null));
                //获取父级菜单
                List<SysMenu> parentMenus = userMapper.findMenus(null);
                //获取子级菜单
                parentMenus.forEach(item -> {
                    List<SysMenu> childrenMenu = userMapper.findChildrenMenu(item.getId(), null);
                    item.setChildren(childrenMenu);
                });
                user.setMenus(parentMenus);

            } else {
                //非管理员需要查询角色信息
                user.setRoles(userMapper.findRoles(user.getId()));
                user.setPermissions(userMapper.findPermissions(user.getId()));
                //获取父级菜单
                List<SysMenu> parentMenus = userMapper.findMenus(user.getId());
                //获取子级菜单
                parentMenus.forEach(item -> {
                    List<SysMenu> childrenMenu = userMapper.findChildrenMenu(item.getId(), user.getId());
                    item.setChildren(childrenMenu);
                });
                user.setMenus(parentMenus);
            }
            redisUtil.setValueTime("userInfo_" + username,user,5);
        }


        return user;

    }
}
