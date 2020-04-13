package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.exception.MenuException;
import com.rubber.admin.core.system.model.TreeDataModel;

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
     * 通过角色id查询到用户到菜单信息
     * @param roleIds 用户的id
     * @return
     */
    SysMenu findMenuByRoleId(Set<Integer> roleIds);


    /**
     * 获取全部的树形菜单
     * @param status 菜单结构信息
     * @return
     */
    SysMenu getRootAllTree(Integer status);


    /**
     * 保存或者更新菜单信息
     * @param sysMenu sysMenu 系统的菜单信息
     * @throws AdminException 菜单的异常
     */
    void saveOrUpdateMenu(SysMenu sysMenu) throws AdminException;


    /**
     * 删除菜单
     * @param menuId 菜单的id
     * @throws MenuException 异常的信息
     */
    void delMenu(Integer menuId) throws MenuException;


    /**
     * 获取并验证一个菜单信息
     * @param menuId menuId
     * @return 返回的一定不为空
     * @throws MenuException 异常信息 为空或者不存在
     */
    SysMenu getAndVerifyById(Integer menuId) throws MenuException;


    /**
     * 通过菜单id 查询菜单的全部信息
     * 包含用户 权限信息
     *
     * @param menuIds 菜单id
     * @return 返回菜单详细信息
     * @throws MenuException 异常信息
     */
    SysMenu getInfoByMenuId(Integer menuIds) throws MenuException;

    /**
     * 获取菜单的操作树形结构
     * @param menuId 菜单id
     * @return 返回树形结构数据值
     */
    List<TreeDataModel> getMenuOptionKey(Integer menuId);
}
