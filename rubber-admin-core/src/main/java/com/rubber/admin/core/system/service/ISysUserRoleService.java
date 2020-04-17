package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysUserRole;
import com.rubber.admin.core.system.model.SysUserRoleModel;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysUserRoleService extends IBaseService<SysUserRole> {


    /**
     * 设置用户的角色信息
     * @param sysUserRoleModel 用户的角色参数信息
     * @throws AdminException 异常信息
     */
    void saveUserRole(SysUserRoleModel sysUserRoleModel) throws AdminException;


    /**
     * 删除用户的权限
     * @param userId 用户的id
     * @return 返回true表示删除成功  false表示没有删除成功
     */
    boolean removeByUserId(Integer userId);


    /**
     * 通过用户的id 返回用户的角色信息
     * @param userId 用户的id
     * @return 返回角色的id
     */
    SysUserRoleModel getRoleByUserId(Integer userId);


    /**
     * 检测角色是否有关联的用户的信息
     * @param roleId
     * @return
     */
    List<SysUserRole> getListByRoleId(Integer roleId);
}
