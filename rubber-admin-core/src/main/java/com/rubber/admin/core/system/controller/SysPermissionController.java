package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.model.PermissionBean;
import com.rubber.admin.core.system.model.SysRoleMenuModel;
import com.rubber.admin.core.system.model.SysRolePermissionModel;
import com.rubber.admin.core.system.service.ISysPermissionDictService;
import com.rubber.admin.core.system.service.ISysRoleMenuService;
import com.rubber.admin.core.system.service.ISysRolePermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author luffyu
 * Created on 2019-10-31
 *
 * 权限的操作接口
 */
@RestController
@RequestMapping(value = "/sys/permission",name = "permission")
public class SysPermissionController {


    @Resource
    private ISysPermissionDictService sysPermissionDictService;

    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    @Resource
    private ISysRoleMenuService sysRoleMenuService;


    /**
     * 获取系统中所有的权限列表
     * @return 返回权限列表
     */
    @GetMapping("/list")
    public ResultMsg listAll(){
        List<PermissionBean> privilegeBeans = sysPermissionDictService.allPermission();
        return ResultMsg.success(privilegeBeans);
    }



    /**
     * 获取系统中某个角色的全部权限
     * @return 返回权限列表
     */
    @GetMapping("/{role}/info")
    public ResultMsg getPrivilegeByRole(@PathVariable("role")Integer role) throws AdminException {
        SysRolePermissionModel rolePrivilege = sysRolePermissionService.getRolePermission(role);
        return ResultMsg.success(rolePrivilege);
    }



    /**
     * 给角色设置权限列表
     * @param request http请求
     * @return 返回保存的角色权限
     */
    @PostMapping("/role/add")
    public ResultMsg saveRolePermission(@RequestParam SysRolePermissionModel rolePermissionModel, HttpServletRequest request) throws AdminException {
        sysRolePermissionService.saveRolePermission(rolePermissionModel);
        return ResultMsg.success();
    }



    /**
     * 给角色设置权限列表
     * @param sysRoleMenuModel 角色的菜单信息
     * @return 返回保存的角色权限
     */
    @PostMapping("/role-menu/add")
    public ResultMsg saveRoleMenu(@RequestParam SysRoleMenuModel sysRoleMenuModel) throws AdminException {
        sysRoleMenuService.addMenuByRole(sysRoleMenuModel);
        return ResultMsg.success();
    }

}
