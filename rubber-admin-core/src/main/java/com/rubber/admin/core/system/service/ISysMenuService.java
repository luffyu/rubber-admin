package com.rubber.admin.core.system.service;

import com.rubber.admin.core.base.IBaseService;
import com.rubber.admin.core.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rubber.admin.core.enums.StatusEnums;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
public interface ISysMenuService extends IBaseService<SysMenu> {


    /**
     * 通过userId 查询用户的菜单信息
     * @param userId 用户的id
     * @return 用户id所在的菜单信息
     */
    SysMenu findMenuByUserId(Integer userId);

    /**
     * 通过角色id查询到用户到菜单信息
     * @param userId 用户的id
     * @return
     */
    SysMenu findMenuByRoleId(Integer userId);

    /**
     * 获取菜单的全目录 从根目录开始往下读取
     * @return 返回一个跟目录下的所有目录菜单
     */
    SysMenu getAllTree();


    Set<String> findAuthKey(Integer userId);


    /**
     * 查询全部的菜单信息
     * @param status 状态信息
     * @return 返回状态列表
     */
    List<SysMenu> getAll(StatusEnums status);

}
