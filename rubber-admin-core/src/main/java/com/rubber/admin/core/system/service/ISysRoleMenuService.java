package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysRoleMenu;
import com.rubber.admin.core.system.exception.RoleException;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysRoleMenuService extends IBaseService<SysRoleMenu> {



    /**
     * 添加菜单权信息
     * @param sysRole 保存的role
     * @return 返回保存的角色
     * @throws RoleException 异常信息
     */
    List<SysRoleMenu> addRoleMenuOption(SysRole sysRole) throws RoleException;


    /**
     * 通过角色id 查询角色关联的菜单信息
     * @param roleId 角色id
     * @return 返回菜单信息
     */
    List<SysRoleMenu> queryByRoleId(Integer roleId);

    /**
     * 批量查询出角色关联的菜单信息
     * @param roleIds 角色信息
     * @return 返回菜单信息
     */
    List<SysRoleMenu> queryByRoleId(Set<Integer> roleIds);

    /**
     * 通过角色id删除角色信息
     * @param roleId 角色id
     * @return 返回是否删除成功
     */
    boolean removeByRoleId(Integer roleId);


    /**
     * 通过菜单id删除信息
     * @param menuId 菜单信息
     * @return 返回list信息
     */
    List<SysRoleMenu> queryByMenuId(Integer menuId);

}
