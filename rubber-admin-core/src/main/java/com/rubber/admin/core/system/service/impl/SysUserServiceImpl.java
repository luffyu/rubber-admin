package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.plugins.security.PermissionUtils;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.exception.UserException;
import com.rubber.admin.core.system.mapper.SysUserMapper;
import com.rubber.admin.core.system.model.PermissionBean;
import com.rubber.admin.core.system.model.SysUserModel;
import com.rubber.admin.core.system.model.UserInfoModel;
import com.rubber.admin.core.system.service.ISysMenuService;
import com.rubber.admin.core.system.service.ISysRolePermissionService;
import com.rubber.admin.core.system.service.ISysRoleService;
import com.rubber.admin.core.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysUserServiceImpl extends BaseAdminService<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private ISysMenuService sysMenuService;

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    @Override
    public SysUser getByLoginAccount(String loginName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_account",loginName);
        return getOne(queryWrapper);
    }


    @Override
    public SysUser getByUserName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        return getOne(queryWrapper);
    }



    @Override
    public SysUser getAndVerifyById(Integer userId) throws UserException {
        if(userId == null || userId <= 0){
            throw new UserException(AdminCode.PARAM_ERROR,"查询的用户id为空");
        }
        SysUser sysUser = getById(userId);
        if(sysUser == null){
            throw new UserException(AdminCode.USER_NOT_EXIST);
        }
        if(StatusEnums.DELETE == sysUser.getDelFlag()){
            throw new UserException(AdminCode.USER_IS_DELETE);
        }
        if(StatusEnums.DISABLE == sysUser.getStatus()){
            throw new UserException(AdminCode.USER_IS_DISABLE);
        }
        return sysUser;
    }


    @Override
    public UserInfoModel getUserAllInfo(Integer userId) throws AdminException {
        SysUser sysUser = getAndVerifyById(userId);
        UserInfoModel userInfoModel = new UserInfoModel(new SysUserModel(sysUser));
        if(sysUser.getSuperUser() == PermissionUtils.SUPER_ADMIN_FLAG){
            doFindSuperUserAllInfo(userInfoModel);
            return userInfoModel;
        }

        //获取用户的角色信息
        List<SysRole> userRole = sysRoleService.findByUserId(userId);
        userInfoModel.setSysRoles(userRole);

        Set<Integer> roleIds = null;
        //获取菜单信息
        if(CollectionUtil.isNotEmpty(userRole)){
            roleIds = userRole.stream().map(SysRole::getRoleId).collect(Collectors.toSet());
        }
        SysMenu userMenu = sysMenuService.findMenuByRoleId(roleIds);
        userInfoModel.setSysMenu(userMenu);

        List<PermissionBean> rolesPermission = sysRolePermissionService.getRolesPermission(roleIds);
        userInfoModel.setPermissions(rolesPermission);

        return userInfoModel;
    }

    /**
     * 如果是超级管理员则直接返回全部目录
     * @param userInfoModel
     */
    private void doFindSuperUserAllInfo(UserInfoModel userInfoModel){
        SysMenu allTree = sysMenuService.getAllTree();
        userInfoModel.setSysMenu(allTree);
    }




    @Override
    public void setUserPermission(SysUser sysUser){
        if(sysUser == null || sysUser.getUserId() == null){
            return;
        }
        //如果是超级系统管理员则不用调用
        if(sysUser.getSuperUser() == PermissionUtils.SUPER_ADMIN_FLAG){
            return;
        }
        List<SysRole> userRole = sysRoleService.findByUserId(sysUser.getUserId());
        Set<Integer> roleIds = null;
        //获取菜单信息
        if(CollectionUtil.isNotEmpty(userRole)){
            roleIds = userRole.stream().map(SysRole::getRoleId).collect(Collectors.toSet());
            Set<String> roleKeys = userRole.stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
            sysUser.setRoleKeys(roleKeys);
        }
        try {
            List<PermissionBean> rolesPermission = sysRolePermissionService.getRolesPermission(roleIds,false);
            if(CollectionUtil.isNotEmpty(rolesPermission)){
                List<PermissionBean.UnitBean> collect = rolesPermission.stream().map(PermissionBean::getUnitBeans).flatMap(Collection::stream).collect(Collectors.toList());
                Set<String> authorizeKey = collect.stream().map(PermissionBean.UnitBean::getAuthorizeKey).collect(Collectors.toSet());
                sysUser.setPermissions(authorizeKey);
            }
        } catch (RoleException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void addUser(SysUser sysUser) {


    }




}
