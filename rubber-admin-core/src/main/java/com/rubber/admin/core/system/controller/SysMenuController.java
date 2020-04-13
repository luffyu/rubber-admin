package com.rubber.admin.core.system.controller;

import cn.hutool.luffyu.util.result.ResultMsg;
import com.rubber.admin.core.base.BaseAdminController;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.exception.MenuException;
import com.rubber.admin.core.system.model.TreeDataModel;
import com.rubber.admin.core.system.service.ISysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@RestController
@RequestMapping(value = "/sys/menu",name = "menu")
public class SysMenuController extends BaseAdminController {

    @Resource
    private ISysMenuService sysMenuService;


    /**
     * 菜单的分页查询
     * @param json 菜单的查询信息json字符串
     * @return 返回菜单的信息
     */
    @GetMapping("/list")
    public ResultMsg list(String json){
        PageModel pageModel = decodeForJsonString(json);
        return ResultMsg.success(sysMenuService.pageBySelect(pageModel, SysMenu.class, null));
    }


    /**
     * 获取菜单的树形结构
     * @return 返回系统中全部菜单的树形结构
     */
    @GetMapping("/tree")
    public ResultMsg getMenuAllTree(Integer status){
        SysMenu sysMenus = sysMenuService.getRootAllTree(status);
        return ResultMsg.success(sysMenus);
    }



    /**
     * 添加菜单
     * @return 返回添加菜单的基本信息
     */
    @PostMapping("/add")
    public ResultMsg addMenu(@RequestBody SysMenu sysMenu) throws AdminException {
        if(sysMenu.getMenuId() != null){
            throw new MenuException(AdminCode.PARAM_ERROR,"保存的菜单id必须为空");
        }
        sysMenuService.saveOrUpdateMenu(sysMenu);
        return ResultMsg.success();
    }



    /**
     * 更新菜单
     * @return 返回菜单更新的值
     */
    @PostMapping("/update/{menuId}")
    public ResultMsg updateMenu(@PathVariable("menuId")Integer menuId,@RequestBody SysMenu sysMenu) throws AdminException {
        if(menuId == null || menuId <= 0 ){
            throw new MenuException(AdminCode.PARAM_ERROR,"菜单id不存在");
        }
        if(!menuId.equals(sysMenu.getMenuId())){
            throw new MenuException(AdminCode.PARAM_ERROR,"菜单id不存在");
        }
        sysMenuService.saveOrUpdateMenu(sysMenu);
        return ResultMsg.success();
    }


    /**
     * 更新菜单
     * @return 返回菜单更新的值
     */
    @PostMapping("/del/{menuId}")
    public ResultMsg delMenu(@PathVariable("menuId")Integer menuId) throws MenuException {
        if(menuId == null || menuId <= 0 ){
            throw new MenuException(AdminCode.PARAM_ERROR,"菜单id不存在");
        }
        sysMenuService.delMenu(menuId);
        return ResultMsg.success();
    }


    /**
     * 获取菜单详情
     * @return 返回菜单更新的值
     */
    @GetMapping("/info/{menuId}")
    public ResultMsg getMenu(@PathVariable("menuId")Integer menuId) throws MenuException {
        if(menuId == null || menuId <= 0 ){
            throw new MenuException(AdminCode.PARAM_ERROR,"菜单id不存在");
        }
        SysMenu sysMenu = sysMenuService.getInfoByMenuId(menuId);
        return ResultMsg.success(sysMenu);
    }



    /**
     * 获取菜单详情
     * @return 返回菜单更新的值
     */
    @GetMapping("/option-tree")
    public ResultMsg getOptionTree(Integer menuId) throws MenuException {
        List<TreeDataModel> menuOptionKey = sysMenuService.getMenuOptionKey(menuId);
        return ResultMsg.success(menuOptionKey);
    }
}
