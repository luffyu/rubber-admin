package com.rubber.admin.core.system.service.impl;

import cn.hutool.coocaa.util.result.code.SysCode;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.MenuTypeEnums;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.exception.MenuException;
import com.rubber.admin.core.system.mapper.SysMenuMapper;
import com.rubber.admin.core.system.service.ISysMenuService;
import com.rubber.admin.core.tools.ServletUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class SysMenuServiceImpl extends BaseAdminService<SysMenuMapper, SysMenu> implements ISysMenuService {


    /**
     * 获取某个用户的菜单结构
     * @param userId 用户的id
     * @return
     */
    @Override
    public SysMenu findMenuByUserId(Integer userId) {
        if(userId == null || userId <= 0){
            return getRoot();
        }
        List<SysMenu> byUserId = getBaseMapper().findByUserId(userId);
        return getAllTree(byUserId);
    }

    /**
     * 获取某个角色的菜单结构
     * @param roleIds 角色id
     * @return
     */
    @Override
    public SysMenu findMenuByRoleId(Set<Integer> roleIds) {
        List<SysMenu> byRoleId = null;
        if(CollectionUtil.isNotEmpty(roleIds)){
            Integer[] ids = new Integer[roleIds.size()];
            ids = roleIds.toArray(ids);
            byRoleId = getBaseMapper().findByRoleId(ids);
        }
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
    public List<SysMenu> getAll(Integer status) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if(status != null){
            queryWrapper.eq("status",status);
        }
        return list(queryWrapper);
    }


    /**
     * 保存或者目录值
     * @param sysMenu 系统的目录
     */
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void saveOrUpdateMenu(SysMenu sysMenu) throws MenuException {
        if(sysMenu == null){
            throw new MenuException(AdminCode.PARAM_ERROR,"菜单的信息为空");
        }
        Integer parentId = sysMenu.getParentId();
        if(parentId == null){
            throw new MenuException(AdminCode.PARAM_ERROR,"父级菜单为空");
        }
        if (parentId != 0){
            SysMenu parentMenu = getById(parentId);
            if(parentMenu == null){
                throw new MenuException(AdminCode.PARAM_ERROR,"父级菜单[{}]不存在",parentId);
            }
        }
        if (sysMenu.getMenuId() != null){
            doUpdate(sysMenu);
        }else {
            doSave(sysMenu);
        }
    }



    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void delMenu(Integer menuId) throws MenuException {
        SysMenu dbMenu = getById(menuId);
        if(dbMenu == null){
            throw new MenuException(AdminCode.MENU_NOT_EXIST,"菜单{}不存在",menuId);
        }
        //查询有没有子目录
        int childNum = countChildNum(dbMenu.getMenuId());
        if(childNum > 0){
            throw new MenuException(AdminCode.MENU_HAVE_CHILD,"菜单{}存在{}子目录，无法删除",menuId,childNum);
        }
        dbMenu.setDelFlag(StatusEnums.DELETE);
        if(!updateById(dbMenu)){
            throw new MenuException(SysCode.SYSTEM_ERROR,"删除菜单信息失败");
        }
    }



    @Override
    public SysMenu getAndVerifyById(Integer menuId) throws MenuException {
        if(menuId == null){
            throw new MenuException(AdminCode.MENU_NOT_EXIST);
        }
        SysMenu byId = getById(menuId);
        if(byId == null){
            throw new MenuException(AdminCode.MENU_NOT_EXIST);
        }
        return byId;
    }


    /**
     * 菜单信息
     * @param sysMenu
     */
    private void doSave(SysMenu sysMenu) throws MenuException {
        sysMenu.setStatus(StatusEnums.NORMAL);
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();

        sysMenu.setCreateBy(loginUserId);
        sysMenu.setCreateTime(now);
        sysMenu.setUpdateBy(loginUserId);
        sysMenu.setUpdateTime(now);
        if(!save(sysMenu)){
            throw new MenuException(SysCode.SYSTEM_ERROR,"添加菜单信息失败",sysMenu);
        }
    }


    /**
     * 更新菜单的信息
     * @param sysMenu
     * @throws MenuException
     */
    private void doUpdate(SysMenu sysMenu) throws MenuException {
        SysMenu dbMenu = getById(sysMenu.getMenuId());
        if(dbMenu == null){
            throw new MenuException(AdminCode.MENU_NOT_EXIST,"菜单{}不存在",sysMenu.getMenuId());
        }
        if(!updateById(sysMenu)){
            throw new MenuException(SysCode.SYSTEM_ERROR,"更新菜单信息失败");
        }
    }


    /**
     * 把菜单整理成 从ROOT根目录开始的树型结构
     * @param menus 菜单结构信息
     * @return
     */
    public SysMenu getAllTree(List<SysMenu> menus) {
        //获取root根菜单
        SysMenu rootMenu = getRoot();
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


    /**
     * 获取根菜单目录
     * @return
     */
    public static SysMenu getRoot(){
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0);
        sysMenu.setMenuName("我的桌面");
        sysMenu.setStatus(StatusEnums.NORMAL);
        sysMenu.setMenuType(MenuTypeEnums.M);
        return sysMenu;
    }




    @Override
    public boolean updateById(SysMenu entity) {
        if(entity == null){
            return false;
        }
        Date now = new Date();
        Integer loginUserId = ServletUtils.getLoginUserId();
        entity.setUpdateTime(now);
        entity.setUpdateBy(loginUserId);
        return super.updateById(entity);
    }


    /**
     * 通过父id 查询子目录的数量
     * @param parentId 父id
     * @return 子目录的数量
     */
    public int countChildNum(Integer parentId){
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        return count(queryWrapper);
    }

}
