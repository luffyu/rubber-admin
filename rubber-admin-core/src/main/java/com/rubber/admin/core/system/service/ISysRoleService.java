package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.exception.RoleException;

import java.util.List;
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
     * 查询用户的所有角色key值信息
     * @param userId 用户的id
     * @return 返回角色信息
     */
    Set<String> findRoleNameByUserId(Integer userId);


    /**
     * 通过角色的id查询用户的角色信息
     * @param roleId 角色id
     * @return 返回角色信息 否则抛出异常
     */
    SysRole getAndVerifyById(Integer roleId) throws RoleException;

}
