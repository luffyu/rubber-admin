package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysRolePermission;
import com.rubber.admin.core.system.exception.PermissionException;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.model.PermissionBean;
import com.rubber.admin.core.system.model.SysRolePermissionModel;

import java.util.List;
import java.util.Set;

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


    /**
     * 查询多个角色的权限信息 并合并返回
     * @param roleIds 角色ids
     * @return 返回角色的id信息
     * @throws RoleException 异常信息
     */
    List<PermissionBean> getRolesPermission(Set<Integer> roleIds) throws RoleException;
}
