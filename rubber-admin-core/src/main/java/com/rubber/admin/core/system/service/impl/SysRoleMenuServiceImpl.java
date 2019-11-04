package com.rubber.admin.core.system.service.impl;

import cn.hutool.coocaa.util.result.code.SysCode;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysRoleMenu;
import com.rubber.admin.core.system.exception.MenuException;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.mapper.SysRoleMenuMapper;
import com.rubber.admin.core.system.model.SysRoleMenuModel;
import com.rubber.admin.core.system.service.ISysMenuService;
import com.rubber.admin.core.system.service.ISysRoleMenuService;
import com.rubber.admin.core.system.service.ISysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysRoleMenuServiceImpl extends BaseAdminService<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysMenuService sysMenuService;



    @Override
    public void addMenuByRole(SysRoleMenuModel sysRoleMenuModel) throws AdminException {
        if (sysRoleMenuModel.getRoleId() == null) {
            throw new RoleException(AdminCode.PARAM_ERROR);
        }
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        SysRole sysRole = sysRoleService.getAndVerifyById(sysRoleMenuModel.getRoleId());
        //全部的菜单信息
        Set<Integer> sysMenuIds = sysRoleMenuModel.getMenuIds();
        //验证菜单是否存在
        if(CollectionUtil.isNotEmpty(sysMenuIds)){
            for (Integer menuId:sysMenuIds){
                sysMenuService.getAndVerifyById(menuId);
                sysRoleMenus.add(new SysRoleMenu(sysRole.getRoleId(), menuId));
            }
        }
        doRemoveByRoleId(sysRoleMenuModel.getRoleId());
        if (CollectionUtil.isNotEmpty(sysRoleMenus)) {
            if(!saveBatch(sysRoleMenus)){
                throw new MenuException(SysCode.SYSTEM_ERROR,"保存角色菜单信息失败");
            }
        }
    }



    private void doRemoveByRoleId(Integer roleId) throws MenuException {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        int count = count(queryWrapper);
        int delete = getBaseMapper().delete(queryWrapper);
        if(delete != count){
            throw new MenuException(SysCode.SYSTEM_ERROR,"删除角色菜单信息失败");
        }
    }


}
