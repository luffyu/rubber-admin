package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysRolePrivilege;
import com.rubber.admin.core.system.exception.PrivilegeException;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.model.SysRolePrivilegeModel;

/**
 * <p>
 * 角色权限列表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-11-01
 */
public interface ISysRolePrivilegeService extends IBaseService<SysRolePrivilege> {


    /**
     * 保存用户的角色权限
     * @param rolePrivilegeModel 具体的角色权限信息
     */
    void saveRolePrivilege(SysRolePrivilegeModel rolePrivilegeModel) throws RoleException, PrivilegeException;


    /**
     * 通过角色id查询角色的全部权限信息
     * @param roleId 角色id
     * @return 返回角色的id信息
     */
    SysRolePrivilegeModel getRolePrivilege(Integer roleId) throws RoleException;
}
