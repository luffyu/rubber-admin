package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysRolePermission;
import com.rubber.admin.core.system.exception.PermissionException;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.model.SysRolePermissionModel;

/**
 * <p>
 * 角色权限列表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-11-01
 */
public interface ISysRolePermissionService extends IBaseService<SysRolePermission> {


    /**
     * 保存用户的角色权限
     * @param rolePermissionModel 具体的角色权限信息
     */
    void saveRolePermission(SysRolePermissionModel rolePermissionModel) throws RoleException, PermissionException;


    /**
     * 通过角色id查询角色的全部权限信息
     * @param roleId 角色id
     * @return 返回角色的id信息
     */
    SysRolePermissionModel getRolePermission(Integer roleId) throws RoleException;
}
