package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysRoleMenu;
import com.rubber.admin.core.system.exception.RoleException;

import java.util.List;

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

}
