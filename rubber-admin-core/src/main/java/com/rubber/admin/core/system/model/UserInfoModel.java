package com.rubber.admin.core.system.model;

import com.rubber.admin.core.system.entity.SysDept;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author luffyu
 * Created on 2019-11-04
 *
 * 用户的全部信息
 */
@Data
public class UserInfoModel implements Serializable {

    /**
     * 用户的基本信息
     */
    private SysUser sysUser;

    /**
     * 部门的基本信息
     */
    private SysDept sysDept;

    /**
     * 菜单的基本信息
     */
    private List<SysMenu> sysMenus;

    /**
     * 用户的角色基本信息
     */
    private List<SysRole> sysRoles;


    public UserInfoModel() {
    }

    public UserInfoModel(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
