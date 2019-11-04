package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-11-04
 */
@RestController
@RequestMapping(value = "/sys/user",name = "user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    /**
     * 角色的分页查询
     * @param pageModel 分页查询组件
     * @param request 请求参数
     * @return 返回
     */
    @GetMapping("/list")
    public ResultMsg list(@RequestParam PageModel pageModel, HttpServletRequest request){
        IPage<SysUser> sysRoleIPage = sysUserService.pageBySelect(pageModel, SysUser.class, null);
        return ResultMsg.success(sysRoleIPage);
    }



    /**
     * 角色的保存信息
     * @param sysUser 角色信息
     * @return
     */
    @PostMapping("/add")
    public ResultMsg addUser(SysUser sysUser) throws RoleException {

        return ResultMsg.success();
    }


    /**
     * 更新角色的信息
     * @param userId 角色id
     * @param sysUser 用户的基本信息
     * @return
     */
    @PostMapping("/{userId}/update")
    public ResultMsg updateUser(@PathVariable("userId")Integer userId,SysUser sysUser) throws RoleException {

        return ResultMsg.success();
    }


    /**
     * 更新角色的信息
     * @param userId 角色id
     * @return
     */
    @PostMapping("/{userId}/del")
    public ResultMsg delUser(@PathVariable("userId")Integer userId) throws RoleException {

        return ResultMsg.success();
    }
}
