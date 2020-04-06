package com.rubber.admin.core.system.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.authorize.AuthorizeKeys;
import com.rubber.admin.core.system.entity.SysRoleMenu;
import lombok.Data;

import java.util.Set;

/**
 * <p>角色操作的权限信息</p>
 *
 * @author luffyu
 * @date 2020-04-05 14:32
 **/

@Data
public class RoleOptionAuthorize {

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer menuId;

    /**
     * 当前菜单可以操作的类型
     */
    private Set<String> optionKeys;

    /**
     * 当前菜单可以操作的权限信息
     */
    private Set<String> authorizeKeys;


    public RoleOptionAuthorize() {
    }

    public RoleOptionAuthorize(SysRoleMenu sysRoleMenu) {
        this.roleId = sysRoleMenu.getRoleId();
        this.menuId = sysRoleMenu.getMenuId();
        if(StrUtil.isNotEmpty(sysRoleMenu.getOptionKey())){
            String[] split = StrUtil.split(sysRoleMenu.getOptionKey(), AuthorizeKeys.MEMBER_LINK_KEY);
            this.optionKeys = CollUtil.newHashSet(split);
        }
    }

}
