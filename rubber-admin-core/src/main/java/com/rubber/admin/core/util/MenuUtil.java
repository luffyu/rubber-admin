package com.rubber.admin.core.util;

import com.rubber.admin.core.entity.SysMenu;
import com.rubber.admin.core.enums.MenuTypeEnums;
import com.rubber.admin.core.enums.StatusEnums;

/**
 * @author luffyu
 * Created on 2019-05-15
 */
public class MenuUtil {


    /**
     * 获取根菜单目录
     * @return
     */
    public static SysMenu getRoot(){
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0);
        sysMenu.setMenuName("ROOT");
        sysMenu.setStatus(StatusEnums.NORMAL.key);
        sysMenu.setMenuType(MenuTypeEnums.M);
        return sysMenu;
    }



}
