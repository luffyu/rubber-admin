package com.rubber.admin.core.authorize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rubber.admin.core.authorize.entity.AuthGroupMenu;
import com.rubber.admin.core.exceptions.AdminException;

import java.util.List;
import java.util.Set;

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
     * 通过菜单id 查询全部的配置信息
     * @param menuIds 菜单id
     * @return 返回查询的角色信息
     */
    List<AuthGroupMenu> queryByMenuId(Set<Integer> menuIds);


    /**
     * 保存菜单用户的关联关系
     * @param menuId 菜单id
     * @param authGroupMenus 当前族群信息
     * @return 返回菜单信息
     * @throws AdminException 异常信息
     */
    List<AuthGroupMenu> saveMenuAuthGroup(Integer menuId, Set<String> authGroupMenus) throws AdminException;


    /**
     * 通过菜单id 查询族群的关联关系
     * @param menuId 活动id
     * @return 返回权限数组
     */
    Set<String> getAuthGroupByMenuId(Integer menuId);


    /**
     * 通过菜单id删除信息
     * @param menuId
     * @return
     */
    boolean removeByMenuId(Integer menuId);
}
