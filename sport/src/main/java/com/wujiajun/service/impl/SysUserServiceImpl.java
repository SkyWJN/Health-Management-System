package com.wujiajun.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wujiajun.config.security.service.UserDetailsServiceImpl;
import com.wujiajun.entity.SysRole;
import com.wujiajun.entity.SysUser;
import com.wujiajun.mapper.SysUserMapper;
import com.wujiajun.service.SysUserService;
import com.wujiajun.utils.*;
import com.wujiajun.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wujiajun
 * @date 2023/3/14/ 11:09
 */
@Service
@Slf4j //打印日志
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //spring security 的加密算法
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private RedisUtil redisUtils;

    @Override
    public Result login(LoginVo loginVo) {
        log.info("1.开始登陆");
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
        log.info("2.判断用户是否存在，密码是否正确");
        if (null == userDetails || !passwordEncoder.matches(MD5Utils.md5(loginVo.getPassword()), userDetails.getPassword())) {
            return Result.fail("账号或密码错误，请重新输入!");
        }
        if (!userDetails.isEnabled()) {
            return Result.fail("该账号已禁用，请联系管理员!");
        }
        log.info("登陆成功，在security对象中存入登陆者信息");
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        log.info("根据登录信息获取token");
        //借助jwt来生成token
        String token = tokenUtil.generateToken(userDetails);
        Map<String, String> map = new HashMap<>(2);
        map.put("tokenHead", tokenHead);
        map.put("token", token);

        return Result.success("登陆成功", map);
    }

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }

    /**
     * 微信小程序登录
     * @param openid 登录参数： 账号和密码
     * @return
     */
    @Override
    public Result miniLogin(String openid, String sessionKey) {
        UserDetails userDetails;
//        userDetails = userDetailsService.loadUserByUsername(openid);
        SysUser user = sysUserMapper.findByUsername(openid);
        if (user == null) {
            sysUserMapper.insertOpenid(openid);
        }
        userDetails = userDetailsService.loadUserByUsername(openid);
        if (!userDetails.isEnabled()) {
            return Result.fail("该账号未启用，请联系管理员！");
        }
        log.info("微信小程序登录成功，在security对象中存入登陆者信息");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登录信息获取token");
        //需要借助jwt来生成token
        String token = tokenUtil.generateToken(userDetails);
        Map<String, Object> map = new HashMap<>(2);
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        map.put("userInfo", userDetails);
        map.put("openid", openid);
        map.put("sessionKey", sessionKey);
        return Result.success("登录成功！", map);
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("分页查询--> 页码==>{}, 页数大小==>{}", queryInfo.getPageNumber(), queryInfo.getPageSize());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<SysUser> page = sysUserMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysUser> result = page.getResult();

        result.forEach(item -> {
            item.setRoles(sysUserMapper.findRoles(item.getId()));
//            item.setMenus(sysUserMapper.findMenus(item.getId()));
//            item.setPermissions(sysUserMapper.findPermissions(item.getId()));
            item.setName(item.getUsername());
        });

        return PageResult.pageResult(total, result);
    }

    @Transactional
    @Override
    public Result insert(SysUser user) {
        log.info("根据用户名查询用户信息");
        SysUser user1 = sysUserMapper.findByUsername(user.getUsername());
        if (null != user1) {
            return Result.fail("用户名已存在!");
        }
        log.info("给密码加密");
        user.setPassword(passwordEncoder.encode(MD5Utils.md5(user.getPassword())));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("1.添加用户信息");
        sysUserMapper.insert(user);
        log.info("2.添加角色信息");
        List<SysRole> roles = user.getRoles();
        if (roles.size() > 0) {
            roles.forEach(item -> {
                sysUserMapper.insertUserRoles(user.getId(), item.getId());
            });
        }
        log.info("3. 用户角色添加成功有{}个", roles.size());
        return Result.success("用户添加成功!");
    }

    @Transactional
    @Override
    public Result update(SysUser user) {
        log.info("先删除角色信息");
        sysUserMapper.deleteRolesByUserId(user.getId());
        log.info("添加新的角色信息");
        List<SysRole> roles = user.getRoles();
        if (roles.size() > 0) {
            roles.forEach(item -> {
                sysUserMapper.insertUserRoles(user.getId(), item.getId());
            });
        }
        log.info("修改用户信息");
//        user.setPassword(passwordEncoder.encode(MD5Utils.md5(user.getPassword())));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sysUserMapper.update(user);
        return Result.success("用户信息修改成功！");
    }

    @Override
    public Result delete(Long id) {
        SysUser user = sysUserMapper.findById(id);
        if(null == user){
            return Result.fail("用户ID不存在");
        }
        sysUserMapper.deleteRolesByUserId(id);
        sysUserMapper.delete(id);
        return Result.success("删除用户成功");
    }

    @Override
    public void updatePwdByMail(String email, String password) {
        log.info("邮箱修改密码");
        sysUserMapper.updatePwdByMail(email, password);
    }

    @Override
    public Result updateByopenId(SysUser user) {
        if (StringUtils.isEmpty(user.getOpenId())) {
            return Result.fail("请传递小程序唯一标识");
        }
        // 清除用户缓存，从新获取
        redisUtils.delKey("username_" + user.getOpenId());
        sysUserMapper.updateByopenId(user);
        return Result.success("用户信息更新成功");
    }
}
