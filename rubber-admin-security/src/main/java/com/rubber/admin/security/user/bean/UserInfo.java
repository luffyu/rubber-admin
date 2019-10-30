package com.rubber.admin.security.user.bean;

import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.model.SysUserModel;
import lombok.Data;

/**
 * @author luffyu
 * Created on 2019-10-23
 * 用户信息的类
 */

@Data
public class UserInfo {

    /**
     * 用户的基本信息
     */
    private SysUserModel user;

    /**
     * 菜单信息
     */
    private SysMenu menu;


    public UserInfo() {
    }

    public UserInfo(SysUserModel sysUserModel, SysMenu uMenu) {
        this.user = sysUserModel;
        this.menu = uMenu;
    }
}
