package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.model.PrivilegeBean;
import com.rubber.admin.core.system.model.SysRolePrivilegeModel;
import com.rubber.admin.core.system.service.ISysPrivilegeDictService;
import com.rubber.admin.core.system.service.ISysRolePrivilegeService;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping(value = "/sys/privilege",name = "privilege")
public class PrivilegeController {


    @Resource
    private ISysPrivilegeDictService sysPrivilegeDictService;

    @Resource
    private ISysRolePrivilegeService sysRolePrivilegeService;


    /**
     * 获取系统中所有的权限列表
     * @param request 请求参数
     * @return 返回权限列表
     */
    @GetMapping("/list")
    public ResultMsg listAll(HttpServletRequest request){
        List<PrivilegeBean> privilegeBeans = sysPrivilegeDictService.allPrivilege();
        return ResultMsg.success(privilegeBeans);
    }




    /**
     * 获取系统中所有的权限列表
     * @param request 请求参数
     * @return 返回权限列表
     */
    @GetMapping("/{role}/role-list")
    public ResultMsg listRole(@PathVariable("role")Integer role, HttpServletRequest request){

        return ResultMsg.success(null);
    }



    /**
     * 添加权限列表
     * @param request http请求
     * @return 返回保存的角色权限
     */
    @PostMapping("/save")
    public ResultMsg save(@RequestParam SysRolePrivilegeModel rolePrivilegeModel, HttpServletRequest request) throws AdminException {
        sysRolePrivilegeService.saveRolePrivilege(rolePrivilegeModel);
        return ResultMsg.success();
    }
}
