package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.exception.MenuException;
import com.rubber.admin.core.system.service.ISysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@RestController
@RequestMapping(value = "/sys/menu",name = "menu")
public class MenuController  {

    @Resource
    private ISysMenuService sysMenuService;


    /**
     * 菜单的分页查询
     * @param pageModel 菜单的查询信息
     * @return 返回菜单的信息
     */
    @GetMapping("/list")
    public ResultMsg list(@RequestParam PageModel pageModel){
        IPage<SysMenu> sysRoleIPage = sysMenuService.pageBySelect(pageModel, SysMenu.class, null);
        return ResultMsg.success(sysRoleIPage);
    }


    /**
     * 获取菜单的树形结构
     * @return 返回系统中全部菜单的树形结构
     */
    @GetMapping("/tree")
    public ResultMsg getMenuAllTree(){
        SysMenu allTree = sysMenuService.getAllTree();
        return ResultMsg.success(allTree);
    }



    /**
     * 添加菜单
     * @return 返回添加菜单的基本信息
     */
    @PostMapping("/add")
    public ResultMsg addMenu(SysMenu sysMenu) throws MenuException {
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
    @PostMapping("/{menuId}/update")
    public ResultMsg updateMenu(@PathVariable("menuId")Integer menuId,SysMenu sysMenu) throws MenuException {
        if(menuId == null || menuId <= 0 ){
            throw new MenuException(AdminCode.PARAM_ERROR,"菜单id不存在");
        }
        if(menuId.equals(sysMenu.getMenuId())){
            throw new MenuException(AdminCode.PARAM_ERROR,"菜单id不存在");
        }
        sysMenuService.saveOrUpdateMenu(sysMenu);
        return ResultMsg.success();
    }


    /**
     * 更新菜单
     * @return 返回菜单更新的值
     */
    @PostMapping("/{menuId}/del")
    public ResultMsg delMenu(@PathVariable("menuId")Integer menuId) throws MenuException {
        if(menuId == null || menuId <= 0 ){
            throw new MenuException(AdminCode.PARAM_ERROR,"菜单id不存在");
        }
        sysMenuService.delMenu(menuId);
        return ResultMsg.success();
    }
}
