package com.rubber.admin.core.authorize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rubber.admin.core.authorize.entity.AuthGroupMenu;

import java.util.List;

/**
 * <p>
 * 权限族和菜单管理表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2020-03-13
 */
public interface IAuthGroupMenuService extends IService<AuthGroupMenu> {

    /**
     * 通过菜单id 查询全部的权限族群和菜单关联关系
     * @param menuId 菜单id
     * @return 返回关联信息
     */
    List<AuthGroupMenu> queryByMenuId(Integer menuId);


    /**
     * 保存菜单用户的关联关系
     * @param menuId 菜单id
     * @param authGroupMenus 当前族群信息
     * @return 返回菜单信息
     */
    boolean saveMenuAuthGroup(Integer menuId,List<AuthGroupMenu> authGroupMenus);
}
