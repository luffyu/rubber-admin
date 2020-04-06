package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.luffyu.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.authorize.AuthorizeKeys;
import com.rubber.admin.core.authorize.AuthorizeTools;
import com.rubber.admin.core.authorize.entity.AuthGroupMenu;
import com.rubber.admin.core.authorize.service.IAuthGroupMenuService;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysRoleMenu;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.mapper.SysRoleMapper;
import com.rubber.admin.core.system.model.RoleOptionAuthorize;
import com.rubber.admin.core.system.service.ISysRoleMenuService;
import com.rubber.admin.core.system.service.ISysRoleService;
import com.rubber.admin.core.tools.ServletUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysRoleServiceImpl extends BaseAdminService<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private ISysRoleMenuService iSysRoleMenuService;

    @Resource
    private IAuthGroupMenuService iAuthGroupMenuService;


    /**
     * 通过用户的id查询角色信息
     * @param userId 用户id
     * @return 返回用户的角色list信息
     */
    @Override
    public List<SysRole> findByUserId(Integer userId) {
        return getBaseMapper().findByUserId(userId);
    }




    @Override
    public SysRole getAndVerifyById(Integer roleId) throws RoleException {
        if(roleId == null){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST);
        }
        SysRole byId = getById(roleId);
        if(byId == null){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST);
        }
        if (byId.getDelFlag() == StatusEnums.DISABLE){
            throw new RoleException(AdminCode.ROLE_IS_DELETE);
        }
        return byId;
    }


    private SysRole getByRoleKey(String roleName)  {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name",roleName);
        return getOne(queryWrapper);
    }


    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public boolean saveOrUpdateRole(SysRole sysRole) throws RoleException {
        if(sysRole == null){
            return false;
        }
        if(sysRole.getRoleId() == null){
            doSave(sysRole);
        }else {
            doUpdate(sysRole);
        }
        iSysRoleMenuService.addRoleMenuOption(sysRole);
        return true;
    }

    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void delRoleById(Integer roleId) throws RoleException {
        SysRole dbRole = getAndVerifyById(roleId);
        if(!removeById(roleId)){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST,"删除角色信息失败",dbRole);
        }
    }


    @Override
    public List<RoleOptionAuthorize> queryRoleAuthorizeKeys(Set<Integer> roleId) {
        List<SysRoleMenu> roleMenus = iSysRoleMenuService.queryByRoleId(roleId);
        return getRoleAuthorizeKeys(roleMenus);
    }

    /**
     * 处理查询的角色关联的菜单信息
     * @param roleMenus 角色的菜单信息
     * @return 返回菜单信息
     */
    private List<RoleOptionAuthorize> getRoleAuthorizeKeys(List<SysRoleMenu> roleMenus){
        //获取角色关联的菜单信息
        if (CollUtil.isEmpty(roleMenus)){
            return null;
        }
        List<RoleOptionAuthorize> roleOptionAuthorizes = new ArrayList<>();

        Set<Integer> needAuthMenus = new HashSet<>();
        for (SysRoleMenu sysRoleMenu:roleMenus){
            roleOptionAuthorizes.add(new RoleOptionAuthorize(sysRoleMenu));
            if (StrUtil.isEmpty(sysRoleMenu.getOptionKey())){
                continue;
            }
            needAuthMenus.add(sysRoleMenu.getMenuId());
        }
        if (CollectionUtil.isNotEmpty(needAuthMenus)){
            doFindRoleMenuAuthorize(needAuthMenus,roleOptionAuthorizes);
        }
        return roleOptionAuthorizes;
    }

    /**
     * 查询权限的信息
     * @param needAuthMenus
     * @param roleOptionAuthorizes
     */
    private void doFindRoleMenuAuthorize(Set<Integer> needAuthMenus,List<RoleOptionAuthorize> roleOptionAuthorizes){
        List<AuthGroupMenu> authGroupMenus = iAuthGroupMenuService.queryByMenuId(needAuthMenus);
        if (CollUtil.isEmpty(authGroupMenus)){
            return;
        }
        Map<String, String> menuApplyOptionKey = authGroupMenus.stream().collect(Collectors.toMap(
                i -> { return i.getMenuId() + AuthorizeKeys.UNDER_LINE_KEY +i.getOptionKey(); },
                AuthGroupMenu::getRelatedApply));
        for (RoleOptionAuthorize roleOptionAuthorize:roleOptionAuthorizes){
            if (CollUtil.isEmpty(roleOptionAuthorize.getOptionKeys())){
                continue;
            }
            Set<String> authorizeKeys = new HashSet<>();
            for (String option:roleOptionAuthorize.getOptionKeys()){
                String key = roleOptionAuthorize.getMenuId() + AuthorizeKeys.UNDER_LINE_KEY + option;
                String applies = menuApplyOptionKey.get(key);
                if (StringUtils.isEmpty(applies)){
                    continue;
                }
                String[] appliesArray = applies.split(AuthorizeKeys.MEMBER_LINK_KEY);
                for (String apply:appliesArray){
                    authorizeKeys.add(AuthorizeTools.createAuthKey(apply,option));
                }
            }
            roleOptionAuthorize.setAuthorizeKeys(authorizeKeys);
        }
    }

    /**
     * 保存角色信息
     * @param sysRole
     * @return
     */
    private boolean doSave(SysRole sysRole) throws RoleException {
        //验证key是否已经存在
        SysRole byRoleKey = getByRoleKey(sysRole.getRoleName());
        if(byRoleKey != null){
            throw new RoleException(AdminCode.ROLE_NAME_EXIST);
        }
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();

        sysRole.setCreateBy(loginUserId);
        sysRole.setCreateTime(now);
        sysRole.setUpdateBy(loginUserId);
        sysRole.setUpdateTime(now);
        if(!save(sysRole)){
            throw new RoleException(SysCode.SYSTEM_ERROR,"添加角色信息{}失败",sysRole);
        }
        return true;
    }


    private boolean doUpdate(SysRole sysRole) throws RoleException {
        SysRole dbRole = getAndVerifyById(sysRole.getRoleId());

        dbRole.setRoleName(sysRole.getRoleName());
        dbRole.setRemark(sysRole.getRemark());
        dbRole.setSeq(sysRole.getSeq());
        dbRole.setStatus(sysRole.getStatus());

        if(!updateById(dbRole)){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST,"更新角色信息失败",dbRole);
        }
        return true;
    }



    @Override
    public boolean updateById(SysRole entity) {
        if(entity == null){
            return false;
        }
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();
        entity.setUpdateBy(loginUserId);
        entity.setUpdateTime(now);
        return super.updateById(entity);
    }




}
