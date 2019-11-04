package com.rubber.admin.core.system.model;

import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.entity.SysRole;
import lombok.Data;

import java.util.List;

/**
 * @author luffyu
 * Created on 2019-11-04
 *
 * 用户的全部信息
 */
@Data
public class UserInfoModel {

    /**
     * 用户的基本信息
     */
    private SysUserModel sysUser;

    /**
     * 菜单的基本信息
     */
    private SysMenu sysMenu;

    /**
     * 用户的角色基本信息
     */
    private List<SysRole> sysRoles;

    /**
     * 用户的权限列表信息
     */
    private List<PermissionBean> permissions;



    public UserInfoModel(SysUserModel sysUser) {
        this.sysUser = sysUser;
    }
}
