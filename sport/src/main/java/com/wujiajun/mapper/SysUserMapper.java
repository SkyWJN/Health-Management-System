package com.wujiajun.mapper;

import com.github.pagehelper.Page;
import com.wujiajun.entity.SysMenu;
import com.wujiajun.entity.SysPermission;
import com.wujiajun.entity.SysRole;
import com.wujiajun.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户相关操作
 *
 * @author wujiajun
 * @date 2023/3/14/ 10:56
 */
public interface SysUserMapper {

    /**
     * 根据用户名查询用户信息
     *
     * @param username SQL中参数占位符的名称
     * @return
     */
    SysUser findByUsername(@Param("value") String username);

    /**
     * 查找角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> findRoles(Long userId);

    /**
     * 根据用户ID查询菜单信息
     *
     * @param userId
     * @return
     */
    List<SysMenu> findMenus(Long userId);

    /**
     * 根据父级ID和用户ID查询子菜单信息
     *
     * @param parent_id
     * @param userId
     * @return
     */
    List<SysMenu> findChildrenMenu(@Param("parent_id") Long parent_id, @Param("userId") Long userId);

    /**
     * 根据用户ID查询权限信息
     *
     * @param userId
     * @return
     */
    List<SysPermission> findPermissions(Long userId);

    /**
     * 分页查询用户信息
     * @param queryString 分页查询字符串
     * @return 返回分页数据
     */
    Page<SysUser> findPage(String queryString);

    /**
     * 添加用户信息
     * @param user 用户信息
     */
    void insert(SysUser user);

    /**
     * 微信小程序进入添加信息
     * @param openid 微信小程序唯一标识
     */
    @Insert("insert into sys_user(open_id, status) values (#{openid}, 1)")
    void insertOpenid(@Param("openid") String openid);

    /**
     * 修改用户信息
     * @param user 用户信息
     */
    void update(SysUser user);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void delete(Long id);

    /**
     * 根据用户信息中的角色列表，去添加用户的角色
     * @param userId
     * @param roleId
     */
    void insertUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 根据用户ID去删除角色列表
     * @param userId
     */
    void deleteRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询用户的基本信息
     * @param id
     * @return
     */
    SysUser findById(Long id);

    /**
     * 根据邮件修改密码
     * @param email 邮件
     * @param password 新密码
     */
    @Update("update sys_user set `password` = #{password} where email = #{email}")
    void updatePwdByMail(@Param("email") String email, @Param("password") String password);

    /**
     * 根据openid更新用户信息
     * @param user
     */
    void updateByopenId(SysUser user);

}
