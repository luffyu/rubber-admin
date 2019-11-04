package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysRoleMenu;
import com.rubber.admin.core.system.model.SysRoleMenuModel;

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
     * 添加用户的菜单信息
     * @param sysRoleMenuModel 系统的角色菜单
     */
    void addMenuByRole(SysRoleMenuModel sysRoleMenuModel) throws AdminException;

}
