package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.luffyu.util.ArrayHashMap;
import cn.hutool.luffyu.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.authorize.AuthorizeKeys;
import com.rubber.admin.core.authorize.service.IAuthGroupConfigService;
import com.rubber.admin.core.authorize.service.IAuthGroupMenuService;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.MenuTypeEnums;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.entity.SysRoleMenu;
import com.rubber.admin.core.system.exception.MenuException;
import com.rubber.admin.core.system.mapper.SysMenuMapper;
import com.rubber.admin.core.system.model.TreeDataModel;
import com.rubber.admin.core.system.service.ISysMenuService;
import com.rubber.admin.core.system.service.ISysRoleMenuService;
import com.rubber.admin.core.tools.ServletUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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


    @Resource
    private IAuthGroupMenuService iAuthGroupMenuService;

    @Resource
    private IAuthGroupConfigService iAuthGroupConfigService;

    @Resource
    private ISysRoleMenuService iSysRoleMenuService;


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



    @Override
    public SysMenu getRootAllTree(Integer status) {
        List<SysMenu> all = getAll(status);
        return getAllTree(all);
    }


    /**
     * 查询全部的菜单信息
     * @param status 状态信息
     * @return 返回状态列表
     */
    private List<SysMenu> getAll(Integer status) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if(status != null){
            queryWrapper.eq("status",status);
        }
        queryWrapper.orderByDesc("seq");
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
    public void saveOrUpdateMenu(SysMenu sysMenu) throws AdminException {
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
        // TODO: 2020/3/14 后面需要通过全局配置参数来确认是否需要执行权限信息
        iAuthGroupMenuService.saveMenuAuthGroup(sysMenu.getMenuId(),sysMenu.getMenuOptionGroup());

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
        //看菜单是否被角色关联
        List<SysRoleMenu> roleMenus = iSysRoleMenuService.queryByMenuId(menuId);
        if (!CollUtil.isEmpty(roleMenus)){
            throw new MenuException(AdminCode.MENU_DELETE_ERROR,"菜单被{}个角色关联",roleMenus.size());
        }
        //查询有没有子目录
        int childNum = countChildNum(dbMenu.getMenuId());
        if(childNum > 0){
            throw new MenuException(AdminCode.MENU_HAVE_CHILD,"菜单{}存在{}个子目录，无法删除",menuId,childNum);
        }
        //删除菜单的关联信息
        if(!iAuthGroupMenuService.removeByMenuId(menuId)){
            throw new MenuException(AdminCode.MENU_DELETE_ERROR,"删除菜单关联权限信息失败");
        }
        //删除菜单
        if(!removeById(menuId)){
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




    @Override
    public SysMenu getInfoByMenuId(Integer menuIds) throws MenuException {
        if (menuIds == null){
            return null;
        }
        SysMenu sysMenu = getAndVerifyById(menuIds);
        if (sysMenu != null){
            // TODO: 2020/3/14 需要通过全局参数来判断权限信息
            Set<String> groupByMenuId = iAuthGroupMenuService.getAuthGroupByMenuId(menuIds);
            sysMenu.setMenuOptionGroup(groupByMenuId == null ? new HashSet<>(1) : groupByMenuId);

        }
        return sysMenu;
    }



    @Override
    public List<TreeDataModel> getMenuOptionKey(Integer menuId) {
        List<SysMenu> menuAuthGroupList = baseMapper.queryMenuAndAuthGroup(StatusEnums.NORMAL);
        if (CollUtil.isEmpty(menuAuthGroupList)){
            return null;
        }
        //把一个父类放到一起
        Map<Integer, List<SysMenu>> collect = menuAuthGroupList.stream().collect(Collectors.groupingBy(SysMenu::getParentId));
        List<SysMenu> rootSysMenu = collect.get(0);
        if(CollUtil.isEmpty(rootSysMenu)){
            return null;
        }
        Map<String, String> optionMap = iAuthGroupConfigService.getOptionMap();

        List<TreeDataModel> dataModels = new ArrayList<>();
        for(SysMenu sysMenu:rootSysMenu){
            TreeDataModel byMenu = new TreeDataModel(String.valueOf(sysMenu.getMenuId()),sysMenu.getMenuName());
            findChildren(sysMenu,byMenu,collect,optionMap);
            dataModels.add(byMenu);
        }
        return dataModels;
    }

    /**
     * 查询出list的结果信息
     * @param sysMenu 系统菜单信息
     * @param collect 整理的结果信息
     */
    private TreeDataModel findChildren(SysMenu sysMenu,TreeDataModel treeDataModel,Map<Integer, List<SysMenu>> collect,Map<String, String> optionMap){
        List<SysMenu> childrenList = collect.get(sysMenu.getMenuId());
        if(childrenList != null){
            List<TreeDataModel> tmp = new ArrayList<>();
            for(SysMenu menu:childrenList){
                TreeDataModel dataModel = findChildren(menu,new TreeDataModel(String.valueOf(menu.getMenuId()),menu.getMenuName()) ,collect,optionMap);
                tmp.add(dataModel);
            }
            treeDataModel.setChildren(tmp);
        }else {
            if(CollUtil.isNotEmpty(sysMenu.getOptionKeys())){
                List<TreeDataModel> treeDataModels = new ArrayList<>();
                for(String s:sysMenu.getOptionKeys()){
                    String key = sysMenu.getMenuId() + AuthorizeKeys.AUTH_LINK_KEY + s;
                    String label = optionMap.get(s);
                    treeDataModels.add(new TreeDataModel(key,label == null ? s : label));
                }
                treeDataModel.setChildren(treeDataModels);
            }
        }
        return treeDataModel;
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
            ArrayHashMap<Integer, SysMenu> arrayHashMap = new ArrayHashMap<>();
            menus.forEach(i -> arrayHashMap.putAndAdd(i.getParentId(),i));
            findChildren(rootMenu,arrayHashMap);
        }
        return rootMenu;
    }

    /**
     * 查询出list的结果信息
     * @param sysMenu 系统菜单信息
     * @param arrayHashMap 整理的结果信息
     */
    private void findChildren(SysMenu sysMenu,ArrayHashMap<Integer, SysMenu> arrayHashMap){
        ArrayList<SysMenu> children = arrayHashMap.get(sysMenu.getMenuId());
        if(children != null){
            for(SysMenu menu:children){
                findChildren(menu,arrayHashMap);
            }
            sysMenu.setChildren(children);
        }
    }


    /**
     * 获取根菜单目录
     * @return
     */
    public static SysMenu getRoot(){
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0);
        sysMenu.setMenuName("根目录");
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
