package com.rubber.admin.security.user.bean;

import com.rubber.admin.core.system.entity.SysMenu;
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
    private LoginUserDetail uInfo;

    /**
     * 菜单信息
     */
    private SysMenu uMenu;


    public UserInfo() {
    }

    public UserInfo(LoginUserDetail uInfo, SysMenu uMenu) {
        this.uInfo = uInfo;
        this.uMenu = uMenu;
    }
}
