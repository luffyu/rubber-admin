package com.rubber.admin.core.system.service;

import com.rubber.admin.core.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface ISysRoleService extends IService<SysRole> {

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

}
