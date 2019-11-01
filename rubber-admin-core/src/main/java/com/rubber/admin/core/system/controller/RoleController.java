package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysRole;
import com.rubber.admin.core.system.service.ISysRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-11-01
 */
@RestController
@RequestMapping(value = "/sys/role",name = "role")
public class RoleController {


    @Resource
    private ISysRoleService iSysRoleService;


    /**
     * 角色的分页查询
     * @param pageModel 分页查询组件
     * @param request 请求参数
     * @return 返回
     */
    @GetMapping("/list")
    public ResultMsg list(@RequestParam PageModel pageModel, HttpServletRequest request){
        IPage<SysRole> sysRoleIPage = iSysRoleService.pageBySelect(pageModel, SysRole.class, null);
        return ResultMsg.success(sysRoleIPage);
    }

}
