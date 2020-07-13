package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.exception.UserException;
import com.rubber.admin.core.system.model.UserInfoModel;

import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysUserService extends IBaseService<SysUser> {


    /**
     * 通过登陆名称查询用户的登陆信息
     * @param loginName 登陆名称
     * @return 返回用户的登陆信息
     */
    SysUser getByLoginAccount(String loginName);

    /**
     * 通过用户名称查询到用户信息
     * @param userName 用户到名称
     * @return 返回用户到信息
     */
    SysUser getByUserName(String userName);


    /**
     * 获取用户的基本信息 并且验证
     * @param userId 用户的id
     * @return 返回用户信息
     * @throws UserException 异常信息
     */
    SysUser getAndVerifyById(Integer userId) throws UserException;


    /**
     * 验证当前用户是否为null
     * @param userId
     * @return
     * @throws UserException
     */
    SysUser getAndVerifyNullById(Integer userId) throws UserException;

    /**
     * 获取用户的全部信息
     * 包括角色 部门  菜单 权限等信息
     * @param userId 用户的id
     * @return 返回用户的基本信息
     * @throws AdminException 异常信息
     */
    UserInfoModel getUserAllInfo(Integer userId) throws AdminException;


    /**
     * 获取用户的基本信息
     * 包括角色 部门
     * @param userId  用户id
     * @return 返回用户的基本信息
     * @throws AdminException 异常信息
     */
    UserInfoModel getUserInfo(Integer userId) throws AdminException;


    /**
     * 获取用户的基本信息
     * 包括角色 部门
     * @param userId  用户id
     * @param verify 是否验证用户的状态
     * @return 返回用户的基本信息
     * @throws AdminException 异常信息
     */
    UserInfoModel getUserInfo(Integer userId,boolean verify) throws AdminException;



    /**
     * 设置用户的权限
     * @param sysUser 用户信息
     */
    void setUserPermission(SysUser sysUser);


    /**
     * 注册用户的全部信息
     * @param userInfoModel 用户信息
     * @throws AdminException 异常信息
     */
    void saveOrUpdateUserInfo(UserInfoModel userInfoModel) throws AdminException;


    /**
     * 注册用户的基本信息
     * @param sysUser 用户信息
     * @throws AdminException 异常信息
     */
    void saveUser(SysUser sysUser) throws AdminException;


    /**
     * 逻辑删除用户
     * @param userId 用户id
     * @throws UserException 异常的信息
     */
    void delUser(Integer userId) throws UserException;


    /**
     * 通过用户id 查询用户的全部权限信息
     * @param userId 角色id
     * @return 返回权限信息
     */
    Set<String> getAuthorizeKeys(Integer userId);

}
