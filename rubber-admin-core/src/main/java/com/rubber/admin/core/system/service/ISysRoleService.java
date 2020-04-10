package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.model.RoleOptionAuthorize;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysRoleService extends IBaseService<SysRole> {

    /**
     * 通过用户id 查找所有的用户信息
     * @param userId 用户id
     * @return 返回用户的角色信息
     */
    List<SysRole> findByUserId(Integer userId);

    /**
     * 通过角色的id查询用户的角色信息
     * @param roleId 角色id
     * @return 返回角色信息
     * @throws RoleException 否则抛出异常
     */
    SysRole getAndVerifyById(Integer roleId) throws RoleException;


    /**
     * 通过角色id 查询角色的详细信息
     * @param roleId 角色id
     * @return 返回角色的详细信息 包括关联的信息
     * @throws RoleException 异常信息
     */
    SysRole getInfoById(Integer roleId) throws RoleException;


    /**
     * 保存或者更新用户的角色信息
     * @param sysRole 系统的角色信息
     * @return 返回true表示更新成功 false表示更新失败
     * @throws RoleException 异常的信息
     */
    boolean saveOrUpdateRole(SysRole sysRole) throws RoleException;


    /**
     * 删除角色的信息
     * @param roleId 角色id
     * @throws RoleException 异常的信息
     */
    void delRoleById(Integer roleId) throws RoleException;


    /**
     * 通过角色id 获取 权限信息
     * @param roleId 角色id
     * @return 返回角色的权限信息
     */
    List<RoleOptionAuthorize> queryRoleAuthorizeKeys(Set<Integer> roleId);


    /**
     * 合并相同的菜单操作项
     * @param roleOptionAuthorizes 查询出来的权限信息
     * @return
     */
    Map<Integer,Set<String>> margeRoleMenuOptions(List<RoleOptionAuthorize> roleOptionAuthorizes);

}
