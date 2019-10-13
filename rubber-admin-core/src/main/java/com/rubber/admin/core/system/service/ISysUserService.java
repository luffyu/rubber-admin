package com.rubber.admin.core.system.service;

import com.rubber.admin.core.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysUserService extends IService<SysUser> {


    /**
     * 通过登陆名称查询用户的登陆信息
     * @param loginName 登陆名称
     * @return 返回用户的登陆信息
     */
    SysUser getByLoginName(String loginName);

    /**
     * 通过用户名称查询到用户信息
     * @param userName 用户到名称
     * @return 返回用户到信息
     */
    SysUser getByUserName(String userName);



    /**
     * 保存用户的基本信息
     * @param sysUser 系统的用户信息
     */
    void checkAndUpdate(SysUser sysUser);


}