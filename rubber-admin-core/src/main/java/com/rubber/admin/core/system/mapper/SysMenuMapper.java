package com.rubber.admin.core.system.mapper;

import com.rubber.admin.core.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    /**
     * 通过用户的userId 找到用户的菜单信息
     * @param roleId 通过角色id 查询菜单的信息
     * @return 返回用户的菜单信息
     */
    List<SysMenu> findByRoleId(Integer roleId);


    /**
     * 通过userId 查询菜单信息
     * @param userId 用户的菜单信息
     * @return 返回菜单信息
     */
    List<SysMenu> findByUserId(Integer userId);
}
