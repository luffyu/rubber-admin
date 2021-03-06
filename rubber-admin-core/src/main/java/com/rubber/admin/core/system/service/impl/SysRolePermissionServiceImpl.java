package com.rubber.admin.core.system.service.impl;

import cn.hutool.coocaa.util.result.code.SysCode;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.cache.ICacheProvider;
import com.rubber.admin.core.plugins.security.PermissionUtils;
import com.rubber.admin.core.plugins.security.PermissionAuthorizeProvider;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysRolePermission;
import com.rubber.admin.core.system.exception.PermissionException;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.mapper.SysRolePermissionMapper;
import com.rubber.admin.core.system.model.PermissionBean;
import com.rubber.admin.core.system.model.PermissionDictModel;
import com.rubber.admin.core.system.model.SysRolePermissionModel;
import com.rubber.admin.core.system.service.ISysPermissionDictService;
import com.rubber.admin.core.system.service.ISysRolePermissionService;
import com.rubber.admin.core.system.service.ISysRoleService;
import com.rubber.admin.core.tools.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限列表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-11-01
 */
@Slf4j
@Service
public class SysRolePermissionServiceImpl extends BaseAdminService<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysPermissionDictService sysPermissionDictService;

    @Autowired(required = false)
    private ICacheProvider cacheProvider;


    /**
     * 保存用户的角色权限
     * @param rolePermissionModel 具体的角色权限信息
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void saveRolePermission(SysRolePermissionModel rolePermissionModel) throws RoleException, PermissionException {
        if(rolePermissionModel == null ){
            throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"配置的角色信息为空");
        }
        //验证list信息
        Map<String,Set<String>> rolePermissionMap  = doVerifyAndInitialize(rolePermissionModel);
        //更新权限信息
        List<SysRolePermission> rolePermissions = doChangeModelToEntity(rolePermissionModel.getRole(), rolePermissionMap);
        //删除之前的角色权限
        doRemoveOldPermissionByRole(rolePermissionModel.getRole().getRoleId());
        //加入新的权限
        if(CollectionUtil.isNotEmpty(rolePermissionMap)){
            if(!saveBatch(rolePermissions)){
                throw new PermissionException(SysCode.SYSTEM_ERROR,"添加角色{}[{}]的权限失败",rolePermissionModel.getRole().getRoleName(),rolePermissionModel.getRole().getRoleId());
            }
        }
        doUpdateCacheVersion();
    }


    /**
     * 角色的权限发生变化 则更新缓存的版本号 让所有用户重新获取版本信息
     */
    private void doUpdateCacheVersion(){
        if(cacheProvider != null){
            cacheProvider.incrVersion();
        }
    }


    /**
     * 通过角色id查询角色的全部权限信息
     * @param roleId 角色id
     * @return 返回角色的id信息
     */
    @Override
    public SysRolePermissionModel getRolePermission(Integer roleId) throws RoleException {
        SysRole dbRole = sysRoleService.getAndVerifyById(roleId);
        List<SysRolePermission> rolePermissions = queryByRole(roleId);
        List<PermissionBean> privilegeBeans = doChangeEntityToModel(rolePermissions);
        return new SysRolePermissionModel(dbRole,privilegeBeans);
    }



    @Override
    public List<PermissionBean> getRolesPermission(Set<Integer> roleIds) throws RoleException {
        return getRolesPermission(roleIds,true);
    }

    @Override
    public List<PermissionBean> getRolesPermission(Set<Integer> roleIds, boolean verifyIds) throws RoleException {
        if(CollectionUtil.isEmpty(roleIds)){
            return null;
        }
        if(verifyIds){
            for (Integer roleId:roleIds) {
                sysRoleService.getAndVerifyById(roleId);
            }
        }
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",roleIds);
        List<SysRolePermission> sysRolePermissions = list(queryWrapper);
        return doChangeEntityToModel(sysRolePermissions);
    }


    /**
     * 通过角色id查询全部的配置信息
     * @param roleId 角色信息
     * @return 用户的角色信息
     */
    private List<SysRolePermission> queryByRole(Integer roleId){
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        return list(queryWrapper);
    }


    /**
     * 验证数据的有效期
     * @param rolePermissionModel 验证数据的有效性
     */
    private Map<String,Set<String>> doVerifyAndInitialize(SysRolePermissionModel rolePermissionModel) throws RoleException {
        SysRole role = rolePermissionModel.getRole();
        if(role == null || role.getRoleId() == null){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST);
        }
        SysRole dbRole = sysRoleService.getAndVerifyById(role.getRoleId());
        rolePermissionModel.setRole(dbRole);
        //看这些角色是不是都在用户的
        List<PermissionBean> privilegeList = rolePermissionModel.getPermissionList();
        if(privilegeList == null){
            return null;
        }
        //配置的权限信息
        Map<String,Set<String>> rolePermissionMap = new HashMap<>(privilegeList.size() * 2);

        //合并返回的结果信息
        for (PermissionBean privilegeBean:privilegeList){
            String moduleKey = privilegeBean.getModuleKey();
            Set<String> roleModuleUnit = rolePermissionMap.get(moduleKey);
            if(roleModuleUnit != null){
                throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"配置的权限模块{}重复",moduleKey);
            }
            //验证module 和 unit的合法性
            PermissionDictModel dictModel = PermissionAuthorizeProvider.getAllPermissionDictModel().get(moduleKey);
            if(dictModel == null){
                throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"权限不存在模块{}",moduleKey);
            }
            roleModuleUnit = new HashSet<>();
            List<PermissionBean.UnitBean> unitBeans = privilegeBean.getUnitBeans();
            for(PermissionBean.UnitBean unitBean:unitBeans){
                Map<String, PermissionDictModel> dictModelUnitKey = dictModel.getUnitKey();

                if(dictModelUnitKey == null || dictModelUnitKey.get(unitBean.getUnitKey()) == null){
                    throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"权限模块{}下不存在权限单元{}",moduleKey,unitBean.getUnitKey());
                }
                if (roleModuleUnit.contains(unitBean.getUnitKey())){
                    throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"权限模块{}下配置的权限单元{}重复",moduleKey,unitBean.getUnitKey());
                }
                roleModuleUnit.add(unitBean.getUnitKey());
            }
            rolePermissionMap.put(moduleKey,roleModuleUnit);
        }
        return rolePermissionMap;
    }


    /**
     * 把参数信息修改为实体信息
     * @param sysRole 角色信息
     * @param rolePermissionMap map信息
     * @return
     */
    private List<SysRolePermission> doChangeModelToEntity(SysRole sysRole, Map<String,Set<String>> rolePermissionMap){
        if(MapUtil.isEmpty(rolePermissionMap)){
            return null;
        }
        Integer loginUserId = ServletUtils.getLoginUserId();
        List<SysRolePermission> rolePermissions = new ArrayList<>();
        for(String key:rolePermissionMap.keySet()){
            Set<String> unitKeys = rolePermissionMap.get(key);
            String unitString = StrUtil.join(PermissionUtils.UNIT_LINK_KEY,unitKeys);
            rolePermissions.add(new SysRolePermission(sysRole,key,unitString,loginUserId));
        }
        return rolePermissions;

    }



    /**
     * 删除之前的权限信息
     * @param roleId 角色id
     */
    private void doRemoveOldPermissionByRole(Integer roleId) throws PermissionException {
        QueryWrapper<SysRolePermission>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        if(!remove(queryWrapper)){
            throw new PermissionException(SysCode.SYSTEM_ERROR,"更新角色{}的权限失败",roleId);
        }
    }


    /**
     * 把实体信息转化成PermissionBean 的信息
     * @param rolePermissionList 参数信息
     * @return
     */
    private List<PermissionBean> doChangeEntityToModel(List<SysRolePermission> rolePermissionList){
        if(CollectionUtil.isEmpty(rolePermissionList)){
            return null;
        }
        Map<String,Set<String>> unRepeatPermission = new HashMap<>(rolePermissionList.size());
        for(SysRolePermission sysRolePermission:rolePermissionList){
            String modelKey = sysRolePermission.getModule();
            Set<String> unitBeansUnRepeat = unRepeatPermission.computeIfAbsent(modelKey, k -> new HashSet<>());
            String unitArray = sysRolePermission.getUnitArray();
            if(StrUtil.isNotEmpty(unitArray)){
                String[] split = StrUtil.split(unitArray, PermissionUtils.UNIT_LINK_KEY);
                HashSet<String> hashSet = CollectionUtil.newHashSet(split);
                unitBeansUnRepeat.addAll(hashSet);
            }
        }
        //获取权限字典
        Map<String, PermissionDictModel> permissionDict = PermissionAuthorizeProvider.getAllPermissionDictModel();
        List<PermissionBean> permissionBeans = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(unRepeatPermission)){
            for (String modelKey:unRepeatPermission.keySet()){
                PermissionDictModel dictModel = permissionDict.get(modelKey);
                PermissionBean permissionBean = new PermissionBean(modelKey,dictModel);

                Set<String> unitBeans = unRepeatPermission.get(modelKey);
                List<PermissionBean.UnitBean> unitBeansList = unitBeans.stream().map(i -> {
                    return new PermissionBean.UnitBean(i, modelKey, dictModel);
                }).collect(Collectors.toList());
                permissionBean.setUnitBeans(unitBeansList);
                permissionBeans.add(permissionBean);
            }
        }
        return permissionBeans;
    }

}
