package com.rubber.admin.core.authorize.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.authorize.entity.AuthGroupMenu;
import com.rubber.admin.core.authorize.mapper.AuthGroupMenuMapper;
import com.rubber.admin.core.authorize.service.IAuthGroupMenuService;
import com.rubber.admin.core.base.BaseAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        QueryWrapper<AuthGroupMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id",menuId);
        return list(queryWrapper);
    }



    @Override
    public boolean saveMenuAuthGroup(Integer menuId, List<AuthGroupMenu> authGroupMenus) {
        removeByMenuId(menuId);
        if(CollUtil.isNotEmpty(authGroupMenus)){
            saveBatch(authGroupMenus);
        }
        return true;
    }


    /**
     * 删除菜单id为x的关联关系
     * @param menuId 菜单id
     * @return 返回菜单信息
     */
    private boolean removeByMenuId(Integer menuId){
        QueryWrapper<AuthGroupMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id",menuId);
        return remove(queryWrapper);
    }



}
