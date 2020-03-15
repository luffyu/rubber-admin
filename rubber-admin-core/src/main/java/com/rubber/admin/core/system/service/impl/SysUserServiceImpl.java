package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.luffyu.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.plugins.cache.CacheAble;
import com.rubber.admin.core.plugins.cache.ICacheProvider;
import com.rubber.admin.core.plugins.encrypt.IEncryptHandler;
import com.rubber.admin.core.plugins.security.PermissionUtils;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.exception.UserException;
import com.rubber.admin.core.system.mapper.SysUserMapper;
import com.rubber.admin.core.system.model.PermissionBean;
import com.rubber.admin.core.system.model.SysUserRoleModel;
import com.rubber.admin.core.system.model.UserInfoModel;
import com.rubber.admin.core.system.service.*;
import com.rubber.admin.core.tools.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
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

    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Resource
    private IEncryptHandler iEncryptHandler;

    @Autowired(required = false)
    private ICacheProvider cacheProvider;


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
        SysUser sysUser = getAndVerifyNullById(userId);
        if(StatusEnums.DELETE == sysUser.getDelFlag()){
            throw new UserException(AdminCode.USER_IS_DELETE);
        }
        if(StatusEnums.DISABLE == sysUser.getStatus()){
            throw new UserException(AdminCode.USER_IS_DISABLE);
        }
        return sysUser;
    }

    /**
     * 验证当前用户是否为null
     * @param userId
     * @return
     * @throws UserException
     */
    @Override
    public SysUser getAndVerifyNullById(Integer userId) throws UserException {
        if(userId == null || userId <= 0){
            throw new UserException(AdminCode.PARAM_ERROR,"查询的用户id为空");
        }
        SysUser sysUser = getById(userId);
        if(sysUser == null){
            throw new UserException(AdminCode.USER_NOT_EXIST);
        }
        return sysUser;
    }


    @Override
    public UserInfoModel getUserAllInfo(Integer userId) throws AdminException {
        UserInfoModel userInfoModel = this.getUserInfo(userId);

        if(userInfoModel.getSysUser().getSuperUser() == PermissionUtils.SUPER_ADMIN_FLAG){
            doFindSuperUserAllInfo(userInfoModel);
            return userInfoModel;
        }

        Set<Integer> roleIds = null;
        //获取菜单信息
        if(CollectionUtil.isNotEmpty(userInfoModel.getSysRoles())){
            roleIds = userInfoModel.getSysRoles().stream().map(SysRole::getRoleId).collect(Collectors.toSet());
        }
        SysMenu rootMenu = sysMenuService.findMenuByRoleId(roleIds);
        userInfoModel.setSysMenus(rootMenu.getChildren());

        List<PermissionBean> rolesPermission = sysRolePermissionService.getRolesPermission(roleIds);
        userInfoModel.setPermissions(rolesPermission);

        return userInfoModel;
    }


    @Override
    public UserInfoModel getUserInfo(Integer userId) throws AdminException {
        SysUser sysUser = getAndVerifyById(userId);
        UserInfoModel userInfoModel = new UserInfoModel(sysUser);

        //获取用户的角色信息
        List<SysRole> userRole = sysRoleService.findByUserId(userId);
        userInfoModel.setSysRoles(userRole);

        return userInfoModel;
    }

    /**
     * 如果是超级管理员则直接返回全部目录
     * @param userInfoModel
     */
    private void doFindSuperUserAllInfo(UserInfoModel userInfoModel){
        List<SysMenu> allTree = sysMenuService.getAllTree(StatusEnums.NORMAL);
        userInfoModel.setSysMenus(allTree);
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


    /**
     * 添加用户的基本信息
     * @param userInfoModel 用户信息
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void saveOrUpdateUserInfo(UserInfoModel userInfoModel) throws AdminException {
        SysUser sysUser = userInfoModel.getSysUser();
        //验证用户信息
        saveUser(sysUser);

        //更新角色信息
        Set<Integer> roleIds = null;
        List<SysRole> sysRoles = userInfoModel.getSysRoles();
        if (CollectionUtil.isNotEmpty(sysRoles)) {
            roleIds = sysRoles.stream().map(SysRole::getRoleId).collect(Collectors.toSet());
        }
        sysUserRoleService.saveUserRole(new SysUserRoleModel(sysUser.getUserId(),roleIds));
    }



    @Override
    public void saveUser(SysUser sysUser) throws AdminException {
        if(sysUser.getUserId() == null){
            doAddUser(sysUser);
        }else {
            doUpdateUser(sysUser);
        }
    }



    @Override
    public void delUser(Integer userId) throws UserException {
        SysUser sysUser = getAndVerifyById(userId);
        sysUser.setDelFlag(StatusEnums.DELETE);
        if(!updateById(sysUser)){
            throw new UserException(SysCode.SYSTEM_ERROR,"删除用户信息异常");
        }
    }


    /**
     * 新增用户信息
     * @param sysUser
     * @throws AdminException
     */
    private void doAddUser(SysUser sysUser) throws AdminException {
        //验证用户信息
        verifyAndInitUserParam(sysUser);
        //设置用户的角色
        doCreateUserPsw(sysUser);

        if(!save(sysUser)){
            throw new UserException(SysCode.SYSTEM_ERROR,"保存用户信息异常");
        }
    }


    /**
     * 更新用户的基本信息
     * @param sysUser
     * @throws AdminException
     */
    private void doUpdateUser(SysUser sysUser) throws AdminException {
        SysUser dbUser = getAndVerifyNullById(sysUser.getUserId());
        if(!dbUser.getLoginCount().equals(sysUser.getLoginCount())){
            throw new UserException(AdminCode.PARAM_ERROR,"无法修改用户的登陆账户");
        }
        //是否修改用户的密码
        if(StrUtil.isNotEmpty(sysUser.getLoginPwd())){
            dbUser.setLoginPwd(sysUser.getLoginPwd());
            doCreateUserPsw(sysUser);
        }
        dbUser.setStatus(sysUser.getStatus());
        dbUser.setUserName(sysUser.getUserName());
        dbUser.setSex(sysUser.getSex());
        dbUser.setEmail(sysUser.getEmail());
        dbUser.setPhone(sysUser.getPhone());
        dbUser.setDeptId(sysUser.getDeptId());
        dbUser.setAvatar(sysUser.getAvatar());
        dbUser.setRemark(sysUser.getRemark());
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();
        dbUser.setUpdateBy(loginUserId);
        dbUser.setUpdateTime(now);
        if(!updateById(dbUser)){
            throw new UserException(SysCode.SYSTEM_ERROR,"更新用户信息异常");
        }
    }


    /**
     * 创建用户的密码信息
     * @param sysUser 系统的用户信息
     */
    private void doCreateUserPsw(SysUser sysUser){
        String salt = iEncryptHandler.createSalt(6);
        sysUser.setSalt(salt);
        String encrypt = iEncryptHandler.encrypt(sysUser.getLoginPwd(), salt);
        sysUser.setLoginPwd(encrypt);
    }

    /**
     * 验证系统中的用户信息
     * @param sysUser
     */
    private void verifyAndInitUserParam(SysUser sysUser) throws UserException {
        if (StrUtil.isEmpty(sysUser.getLoginAccount()) || StrUtil.isEmpty(sysUser.getLoginAccount())){
            throw new UserException(AdminCode.PARAM_ERROR);
        }
        SysUser oldAccount = getByLoginAccount(sysUser.getLoginAccount());
        if(oldAccount != null){
            throw new UserException(AdminCode.ACCOUNT_IS_EXIST);
        }
        sysUser.setLoginCount(0);
        sysUser.setStatus(StatusEnums.NORMAL);
        sysUser.setDelFlag(StatusEnums.NORMAL);
        sysUser.setSalt(RandomUtil.randomNumbers(6));
        sysUser.setSuperUser(StatusEnums.NORMAL);

        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();

        sysUser.setCreateBy(loginUserId);
        sysUser.setCreateTime(now);
        sysUser.setUpdateBy(loginUserId);
        sysUser.setUpdateTime(now);
    }



    @Override
    public boolean updateById(SysUser entity) {
        if(entity == null){
            return false;
        }
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();
        entity.setUpdateBy(loginUserId);
        entity.setUpdateTime(now);
        boolean updateFlag = super.updateById(entity);
        if(updateFlag){
            doClearCache(entity);
        }
        return updateFlag;
    }


    /**
     * 清空缓存信息
     */
    private void doClearCache(CacheAble cacheAble){
        if(cacheProvider != null){
            cacheProvider.delete(cacheAble.getKey());
        }
    }

}
