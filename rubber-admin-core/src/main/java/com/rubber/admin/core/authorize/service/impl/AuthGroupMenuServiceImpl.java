package com.rubber.admin.core.authorize.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.authorize.AuthorizeKeys;
import com.rubber.admin.core.authorize.entity.AuthGroupMenu;
import com.rubber.admin.core.authorize.exception.AuthGroupException;
import com.rubber.admin.core.authorize.mapper.AuthGroupMenuMapper;
import com.rubber.admin.core.authorize.service.IAuthGroupMenuService;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.exception.MenuException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 权限族和菜单管理表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2020-03-13
 */
@Service
public class AuthGroupMenuServiceImpl extends BaseAdminService<AuthGroupMenuMapper, AuthGroupMenu> implements IAuthGroupMenuService {



    @Override
    public List<AuthGroupMenu> queryByMenuId(Integer menuId) {
        if (menuId == null || menuId <= 0 ){
            return null;
        }
        QueryWrapper<AuthGroupMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id",menuId);
        return list(queryWrapper);
    }

    @Override
    public List<AuthGroupMenu> queryByMenuId(Set<Integer> menuIds) {
        if (menuIds == null || menuIds.size() <= 0){
            return null;
        }
        QueryWrapper<AuthGroupMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("menu_id",menuIds);
        return list(queryWrapper);
    }


    @Override
    public List<AuthGroupMenu> saveMenuAuthGroup(Integer menuId, Set<String> authGroupMenus) throws AdminException {
        if (menuId == null){
            throw new MenuException(AdminCode.MENU_NOT_EXIST);
        }
        removeByMenuId(menuId);
        List<AuthGroupMenu> authGroupMenusList = handleSetToEntity(menuId, authGroupMenus);
        if (CollUtil.isNotEmpty(authGroupMenusList)){
            if(!saveBatch(authGroupMenusList)){
                throw new AuthGroupException(AdminCode.AUTH_GROUP_INSTALL_ERROR,"保存菜单{}的关联权限族群异常",menuId);
            }
        }
        return authGroupMenusList;
    }



    @Override
    public Set<String> getAuthGroupByMenuId(Integer menuId) {
        List<AuthGroupMenu> authGroupMenus = queryByMenuId(menuId);
        if (CollUtil.isEmpty(authGroupMenus)) {
            return null;
        }
        return handelEntityToSet(authGroupMenus);
    }


    /**
     * 把集合变成set
     * @param list list集合信息
     * @return 返回操作信息
     */
    private Set<String> handelEntityToSet(List<AuthGroupMenu> list){
        Set<String> authSet = new HashSet<>();
        for (AuthGroupMenu authGroupMenu:list){
            String optionKey = authGroupMenu.getOptionKey();
            String relatedApply = authGroupMenu.getRelatedApply();
            for (String apply:StrUtil.split(relatedApply,AuthorizeKeys.MEMBER_LINK_KEY)){
                authSet.add(apply + AuthorizeKeys.AUTH_LINK_KEY + optionKey);
            }
        }
        return authSet;
    }



    /**
     * 把set变成集合
     * @param menuId 菜单id
     * @param authGroupMenus 权限新
     * @return 返回实体集合
     */
    private List<AuthGroupMenu> handleSetToEntity(Integer menuId, Set<String> authGroupMenus){
        if (CollUtil.isEmpty(authGroupMenus)){
            return null;
        }
        Map<String,Set<String>> authMap = new HashMap<>(authGroupMenus.size());
        for (String s:authGroupMenus){
            int i = StrUtil.indexOf(s, AuthorizeKeys.AUTH_LINK_KEY.charAt(0));
            if (i < 0){
                continue;
            }
            String apply = s.substring(0,i);
            String option = s.substring(i+1);

            Set<String> relatedApply = authMap.get(option);
            if (relatedApply == null){
                relatedApply = new HashSet<>();
            }
            relatedApply.add(apply);
            authMap.put(option,relatedApply);
        }
        if(MapUtil.isEmpty(authMap)){
            return null;
        }
        List<AuthGroupMenu> authGroupMenusList = new ArrayList<>();
        for (String key:authMap.keySet()){
            Set<String> relatedApply = authMap.get(key);
            if(CollUtil.isEmpty(relatedApply)){
                continue;
            }
            AuthGroupMenu authGroupMenu = new AuthGroupMenu();
            authGroupMenu.setMenuId(menuId);
            authGroupMenu.setOptionKey(key);
            authGroupMenu.setRelatedApply(CollUtil.join(relatedApply,AuthorizeKeys.MEMBER_LINK_KEY));
            authGroupMenusList.add(authGroupMenu);
        }
        return authGroupMenusList;
    }




    /**
     * 删除菜单id为x的关联关系
     * @param menuId 菜单id
     * @return 返回菜单信息
     */
    @Override
    public boolean removeByMenuId(Integer menuId){
        if (menuId == null){
            return false;
        }
        QueryWrapper<AuthGroupMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id",menuId);
        int delete = baseMapper.delete(queryWrapper);
        if (delete <= 0 && count(queryWrapper) > 0){
            return false;
        }
        return true;
    }



}
