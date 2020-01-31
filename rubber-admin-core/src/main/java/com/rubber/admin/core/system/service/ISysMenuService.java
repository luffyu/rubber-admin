package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.exception.MenuException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysMenuService extends IBaseService<SysMenu> {


    /**
     * 通过userId 查询用户的菜单信息
     * @param userId 用户的id
     * @return 用户id所在的菜单信息
     */
    SysMenu findMenuByUserId(Integer userId);

    /**
     * 通过角色id查询到用户到菜单信息
     * @param roleIds 用户的id
     * @return
     */
    SysMenu findMenuByRoleId(Set<Integer> roleIds);

    /**
     * 获取菜单的全目录 从根目录开始往下读取
     * @return 返回一个跟目录下的所有目录菜单
     */
    SysMenu getAllTree(Integer status);


    Set<String> findAuthKey(Integer userId);


    /**
     * 查询全部的菜单信息
     * @param status 状态信息
     * @return 返回状态列表
     */
    List<SysMenu> getAll(Integer status);


    /**
     * 保存或者更新菜单信息
     * @param sysMenu 系统的菜单信息
     * @throws MenuException 菜单的异常
     */
    void saveOrUpdateMenu(SysMenu sysMenu) throws MenuException;


    /**
     * 删除菜单
     * @param menuId 菜单的id
     * @throws MenuException 异常的信息
     */
    void delMenu(Integer menuId) throws MenuException;


    /**
     * 获取并验证一个菜单信息
     * @param menuId menuId
     * @return
     * @throws MenuException
     */
    SysMenu getAndVerifyById(Integer menuId) throws MenuException;


    /**
     * 补全菜单的树形结构
     * @return 返回补全之后的菜单信息
     */
    List<SysMenu> completionMenuTree(List<SysMenu> sysMenus) throws MenuException;


    /**
     * 通过菜单id批量查询菜单信息
     * @param menuIds 菜单ids
     * @return 返回菜单信息
     * @throws MenuException
     */
    List<SysMenu> queryVerifyByIds(Set<Integer> menuIds) throws MenuException;
}
