package com.rubber.admin.core.system.mapper;

import com.rubber.admin.core.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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
     * @param roleIds 通过角色id 查询菜单的信息
     * @return 返回用户的菜单信息
     */
    List<SysMenu> findByRoleId(@Param("roleIds") Integer[] roleIds);


    /**
     * 通过userId 查询菜单信息
     * @param userId 用户的菜单信息
     * @return 返回菜单信息
     */
    List<SysMenu> findByUserId(Integer userId);


    /**
     * 查询菜单的查询信息
     * @param status 状态值
     * @return 返回系统菜单
     */
    List<SysMenu> queryMenuAndAuthGroup(@Param("status")Integer status);
}
