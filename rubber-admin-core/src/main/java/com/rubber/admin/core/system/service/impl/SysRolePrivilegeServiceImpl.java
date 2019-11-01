package com.rubber.admin.core.system.service.impl;

import cn.hutool.coocaa.util.result.code.SysCode;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.security.PrivilegeAuthorizeProvider;
import com.rubber.admin.core.plugins.security.PrivilegeUtils;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysRolePrivilege;
import com.rubber.admin.core.system.exception.PrivilegeException;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.mapper.SysRolePrivilegeMapper;
import com.rubber.admin.core.system.model.PrivilegeBean;
import com.rubber.admin.core.system.model.SysRolePrivilegeModel;
import com.rubber.admin.core.system.service.ISysRolePrivilegeService;
import com.rubber.admin.core.system.service.ISysRoleService;
import com.rubber.admin.core.tools.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
public class SysRolePrivilegeServiceImpl extends BaseAdminService<SysRolePrivilegeMapper, SysRolePrivilege> implements ISysRolePrivilegeService {

    @Resource
    private ISysRoleService sysRoleService;


    /**
     * 保存用户的角色权限
     * @param rolePrivilegeModel 具体的角色权限信息
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void saveRolePrivilege(SysRolePrivilegeModel rolePrivilegeModel) throws RoleException, PrivilegeException {
        if(rolePrivilegeModel == null ){
            throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"配置的角色信息为空");
        }
        //验证list信息
        Map<String,Set<String>> rolePrivilegeMap  = doVerifyAndInitialize(rolePrivilegeModel);
        //更新权限信息
        List<SysRolePrivilege> rolePrivileges = doChangeModelToEntity(rolePrivilegeModel.getRole(), rolePrivilegeMap);
        //删除之前的角色权限
        doRemoveOldPrivilegeByRole(rolePrivilegeModel.getRole().getRoleId());
        //加入新的权限
        if(CollectionUtil.isNotEmpty(rolePrivilegeMap)){
            if(!saveBatch(rolePrivileges)){
                throw new PrivilegeException(SysCode.SYSTEM_ERROR,"添加角色{}[{}]的权限失败",rolePrivilegeModel.getRole().getRoleName(),rolePrivilegeModel.getRole().getRoleId());
            }
        }
    }




    /**
     * 通过角色id查询角色的全部权限信息
     * @param roleId 角色id
     * @return 返回角色的id信息
     */
    @Override
    public SysRolePrivilegeModel getRolePrivilege(Integer roleId) throws RoleException {
        SysRole dbRole = sysRoleService.getAndVerifyById(roleId);
        List<SysRolePrivilege> rolePrivileges = queryByRole(roleId);




        return null;
    }


    /**
     * 通过角色id查询全部的配置信息
     * @param roleId 角色信息
     * @return 用户的角色信息
     */
    private List<SysRolePrivilege> queryByRole(Integer roleId){
        QueryWrapper<SysRolePrivilege> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        return list(queryWrapper);
    }


    /**
     * 验证数据的有效期
     * @param rolePrivilegeModel 验证数据的有效性
     */
    private Map<String,Set<String>> doVerifyAndInitialize(SysRolePrivilegeModel rolePrivilegeModel) throws RoleException {
        SysRole role = rolePrivilegeModel.getRole();
        if(role == null || role.getRoleId() == null){
            throw new RoleException(AdminCode.ROLE_NOT_EXIST);
        }
        SysRole dbRole = sysRoleService.getAndVerifyById(role.getRoleId());
        rolePrivilegeModel.setRole(dbRole);
        //看这些角色是不是都在用户的
        List<PrivilegeBean> privilegeList = rolePrivilegeModel.getPrivilegeList();
        if(privilegeList == null){
            return null;
        }
        //配置的权限信息
        Map<String,Set<String>> rolePrivilegeMap = new HashMap<>(privilegeList.size() * 2);

        //合并返回的结果信息
        for (PrivilegeBean privilegeBean:privilegeList){
            String moduleKey = privilegeBean.getModuleKey();
            Set<String> roleModuleUnit = rolePrivilegeMap.get(moduleKey);
            if(roleModuleUnit != null){
                throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"配置的权限模块{}重复",moduleKey);
            }
            //验证module 和 unit的合法性
            Set<String> allUnits = PrivilegeAuthorizeProvider.getAllAuthorize().get(moduleKey);
            if(allUnits == null){
                throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"权限不存在模块{}",moduleKey);
            }
            roleModuleUnit = new HashSet<>();
            List<PrivilegeBean.UnitBean> unitBeans = privilegeBean.getUnitBeans();
            for(PrivilegeBean.UnitBean unitBean:unitBeans){
                if(!allUnits.contains(unitBean.getUnitKey())){
                    throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"权限模块{}下不存在权限单元{}",moduleKey,unitBean.getUnitKey());
                }
                if (!roleModuleUnit.contains(unitBean.getUnitKey())){
                    throw new RoleException(AdminCode.ROLE_PRIVILEGE_ILLEGAL,"权限模块{}下配置的权限单元{}重复",moduleKey,unitBean.getUnitKey());
                }
                roleModuleUnit.add(unitBean.getUnitKey());
            }
            rolePrivilegeMap.put(moduleKey,roleModuleUnit);
        }
        return rolePrivilegeMap;
    }


    /**
     * 把参数信息修改为实体信息
     * @param sysRole 角色信息
     * @param rolePrivilegeMap map信息
     * @return
     */
    private List<SysRolePrivilege> doChangeModelToEntity(SysRole sysRole,Map<String,Set<String>> rolePrivilegeMap){
        if(MapUtil.isEmpty(rolePrivilegeMap)){
            return null;
        }
        Integer loginUserId = ServletUtils.getLoginUserId();
        List<SysRolePrivilege> rolePrivileges = new ArrayList<>();
        for(String key:rolePrivilegeMap.keySet()){
            Set<String> unitKeys = rolePrivilegeMap.get(key);
            String unitString = StrUtil.join(PrivilegeUtils.UNIT_LINK_KEY,unitKeys);
            rolePrivileges.add(new SysRolePrivilege(sysRole,key,unitString,loginUserId));
        }
        return rolePrivileges;

    }



    /**
     * 删除之前的权限信息
     * @param roleId 角色id
     */
    private void doRemoveOldPrivilegeByRole(Integer roleId) throws PrivilegeException {
        QueryWrapper<SysRolePrivilege>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        if(!remove(queryWrapper)){
            throw new PrivilegeException(SysCode.SYSTEM_ERROR,"更新角色{}的权限失败",roleId);
        }
    }





    private List<PrivilegeBean> doChangeEntityToModel(List<SysRolePrivilege> rolePrivilegeList){
        if(CollectionUtil.isEmpty(rolePrivilegeList)){
            return null;
        }
        for(SysRolePrivilege sysRolePrivilege:rolePrivilegeList){
            String modelKey = sysRolePrivilege.getModule();
            PrivilegeBean privilegeBean = new PrivilegeBean(modelKey,modelKey);

            String unitArray = sysRolePrivilege.getUnitArray();
            if(StrUtil.isNotEmpty(unitArray)){
                for(String unitKey:unitArray.split("")){

                }
            }

        }
        return null;
    }

}
