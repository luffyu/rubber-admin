package com.rubber.admin.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.entity.SysMenu;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.mapper.SysMenuMapper;
import com.rubber.admin.core.service.ISysMenuService;
import com.rubber.admin.core.base.BaseService;
import com.rubber.admin.core.util.MenuUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-05-13
 */
@Service
public class SysMenuServiceImpl extends BaseService<SysMenuMapper, SysMenu> implements ISysMenuService {


    /**
     * 获取某个用户的菜单结构
     * @param userId 用户的id
     * @return
     */
    @Override
    public SysMenu findMenuByUserId(Integer userId) {
        List<SysMenu> byUserId = getBaseMapper().findByUserId(userId);
        return getAllTree(byUserId);
    }

    /**
     * 获取某个角色的菜单结构
     * @param roleId
     * @return
     */
    @Override
    public SysMenu findMenuByRoleId(Integer roleId) {
        List<SysMenu> byRoleId = getBaseMapper().findByRoleId(roleId);
        return getAllTree(byRoleId);
    }


    /**
     * 获取菜单的树形结构
     * @return 返回菜单的数型结构
     */
    @Override
    public SysMenu getAllTree(){
        List<SysMenu> all = getAll(StatusEnums.NORMAL);
        return getAllTree(all);
    }

    /**
     * 通过用户id查询到他对应到权限值
     * @param userId
     * @return
     */
    @Override
    public Set<String> findAuthKey(Integer userId) {
        List<SysMenu> byRoleId = getBaseMapper().findByUserId(userId);
        return findUserAuthKey(byRoleId);
    }


    /**
     * 查询全部的菜单信息
     * @param status 状态信息
     * @return 返回状态列表
     */
    @Override
    public List<SysMenu> getAll(StatusEnums status) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if(status != null){
            queryWrapper.eq("status",status.key);
        }
        return list(queryWrapper);
    }



    /**
     * 把菜单整理成 从ROOT根目录开始的树型结构
     * @param menus 菜单结构信息
     * @return
     */
    public SysMenu getAllTree(List<SysMenu> menus) {
        //获取root根菜单
        SysMenu rootMenu = MenuUtil.getRoot();
        if(!CollectionUtils.isEmpty(menus)){
            //标记整理菜单信息
            Map<Integer,List<SysMenu>> map = new HashMap<>();
            menus.forEach(sysMenu -> {
                Integer parentId = sysMenu.getParentId();
                List<SysMenu> classifyMenu = map.get(parentId);
                if(classifyMenu == null){
                    classifyMenu = new ArrayList<>(menus.size());
                }
                classifyMenu.add(sysMenu);
                map.put(parentId,classifyMenu);
            });
            //得到整理的map
            findChildMenu(rootMenu,map);
        }
        return rootMenu;
    }



    /**
     * 返回用户的map信息
     * @param sysMenu 系统的菜单信息
     * @param map
     */
    private void findChildMenu(SysMenu sysMenu, Map<Integer,List<SysMenu>> map){
        List<SysMenu> thisChildMenus = map.get(sysMenu.getMenuId());
        if(!CollectionUtils.isEmpty(thisChildMenus)){
            sysMenu.setChildMenus(thisChildMenus);
            thisChildMenus.forEach( childMenu->{
                //childMenu.setFatherMenu(sysMenu);
                findChildMenu(childMenu,map);
            });
        }
    }


    /**
     * 从菜单中返回用户到权限字段
     * @param sysMenus 菜单信息
     * @return 返回用户到权限字段
     */
    private Set<String> findUserAuthKey(List<SysMenu> sysMenus){
        if(!CollectionUtils.isEmpty(sysMenus)){
            Set<String> authKeys = new HashSet<>(sysMenus.size());
            sysMenus.forEach(sysMenu -> {
                String authKey = sysMenu.getAuthKey();
                if(!StringUtils.isEmpty(authKey)){
                    authKeys.add(authKey);
                }
            });
            return authKeys;
        }
        return new HashSet<>(1);

    }


}
