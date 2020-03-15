package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.luffyu.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.authorize.AuthorizeKeys;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysMenu;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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



    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public List<SysRoleMenu> addRoleMenuOption(SysRole sysRole) throws RoleException {
        doRemoveByRoleId(sysRole.getRoleId());
        List<SysRoleMenu> roleMenus = handleOptionToEntity(sysRole);
        if(CollUtil.isNotEmpty(roleMenus)){
            if(!saveBatch(roleMenus)){
                throw new RoleException(AdminCode.INSTALL_ROLE_MENU_ERROR);
            }
        }
        return roleMenus;
    }



    private List<SysRoleMenu> handleOptionToEntity(SysRole sysRole){
        if (CollUtil.isEmpty(sysRole.getRoleMenuOptions())){
            return null;
        }
        Map<String,Set<String>> menuOption = new HashMap<>();
        for (String roleMenuOption:sysRole.getRoleMenuOptions()){
            int i = StrUtil.indexOf(roleMenuOption, AuthorizeKeys.AUTH_LINK_KEY.charAt(0));
            if (i < 0){
                menuOption.putIfAbsent(roleMenuOption,new HashSet<>());
                continue;
            }
            String menuId = roleMenuOption.substring(0,i);
            String option = roleMenuOption.substring(i+1);
            Set<String> strings = menuOption.get(menuId);
            if (strings == null){
                strings = new HashSet<>();
            }
            strings.add(option);
            menuOption.put(menuId,strings);
        }
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        for (String menuId:menuOption.keySet()){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(Integer.parseInt(menuId));
            sysRoleMenu.setRoleId(sysRole.getRoleId());
            sysRoleMenu.setOptionKey(CollUtil.join(menuOption.get(menuId),AuthorizeKeys.MEMBER_LINK_KEY));
            roleMenus.add(sysRoleMenu);
        }
        return roleMenus;
    }




    private void doRemoveByRoleId(Integer roleId) throws RoleException {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        int count = count(queryWrapper);
        int delete = getBaseMapper().delete(queryWrapper);
        if(delete != count){
            throw new RoleException(SysCode.SYSTEM_ERROR,"删除角色菜单信息失败");
        }
    }


}
