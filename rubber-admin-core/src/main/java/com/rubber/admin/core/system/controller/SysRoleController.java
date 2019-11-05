package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.service.ISysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-11-01
 */
@RestController
@RequestMapping(value = "/sys/role",name = "role")
public class SysRoleController {


    @Resource
    private ISysRoleService iSysRoleService;


    /**
     * 角色的分页查询
     * @param pageModel 分页查询组件
     * @param request 请求参数
     * @return 返回
     */
    @GetMapping("/list")
    public ResultMsg list(@RequestBody PageModel pageModel, HttpServletRequest request){
        IPage<SysRole> sysRoleIPage = iSysRoleService.pageBySelect(pageModel, SysRole.class, null);
        return ResultMsg.success(sysRoleIPage);
    }


    /**
     * 角色的保存信息
     * @param sysRole 角色信息
     * @return
     */
    @PostMapping("/add")
    public ResultMsg addRole(@RequestBody SysRole sysRole) throws RoleException {
        iSysRoleService.saveOrUpdateRole(sysRole);
        return ResultMsg.success();
    }


    /**
     * 更新角色的信息
     * @param roleId 角色id
     * @param sysRole 需要更新的角色实体信息
     * @return
     */
    @PostMapping("/{roleId}/update")
    public ResultMsg updateRole(@PathVariable("roleId")Integer roleId,@RequestBody SysRole sysRole) throws RoleException {
        if(roleId == null || roleId <= 0 ){
            throw new RoleException(AdminCode.PARAM_ERROR,"角色id不存在");
        }
        if(!roleId.equals(sysRole.getRoleId())){
            throw new RoleException(AdminCode.PARAM_ERROR,"角色id不存在");
        }
        iSysRoleService.saveOrUpdateRole(sysRole);
        return ResultMsg.success();
    }


    /**
     * 删除角色的信息
     * @param roleId 角色信息
     * @return
     */
    @PostMapping("/{roleId}/del")
    public ResultMsg delRole(@PathVariable("roleId")Integer roleId) throws RoleException {
        if(roleId == null || roleId <= 0 ){
            throw new RoleException(AdminCode.PARAM_ERROR,"角色id不存在");
        }
        iSysRoleService.delRoleById(roleId);
        return ResultMsg.success();
    }

}
