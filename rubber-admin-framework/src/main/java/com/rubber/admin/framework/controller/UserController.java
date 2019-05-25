package com.rubber.admin.framework.controller;

import com.rubber.admin.core.entity.SysMenu;
import com.rubber.admin.core.entity.SysUser;
import com.rubber.admin.core.model.ResultModel;
import com.rubber.admin.core.service.ISysMenuService;
import com.rubber.admin.framework.shiro.UserInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-05-21
 */

@RestController
@RequestMapping("/u")
public class UserController {

    @Autowired
    private ISysMenuService sysMenuService;


    /**
     * 返回用户的菜单信息
     * @param request request请求
     * @return 返回用户的菜单信息
     */
    @GetMapping("/menu")
    public ResultModel getUserMenu(HttpServletRequest request){
        SysUser sysUser = UserInfoUtils.getLoginUser();
        SysMenu menuByUser = sysMenuService.findMenuByUserId(sysUser.getUserId());
        return ResultModel.createSuccess(menuByUser);
    }


    /**
     * 返回用户的基本信息
     * @param request
     * @return
     */
    @GetMapping("/info")
    public ResultModel getUserInfo(HttpServletRequest request){
        SysUser sysUser = UserInfoUtils.getLoginUser();
        sysUser.setPassword(null);
        sysUser.setSalt(null);
        return ResultModel.createSuccess(sysUser);
    }


    /**
     * 修改用户的密码
     * @param oldPassword 之前的密码
     * @param newPassword 新的米密码
     * @param request  用户的http请求
     * @return 返回用户的基本信息
     */
    @GetMapping("/change-auth")
    public ResultModel changePassword(String oldPassword,String newPassword,HttpServletRequest request){
        SysUser sysUser = UserInfoUtils.getLoginUser();
        sysUser.setPassword(null);
        sysUser.setSalt(null);
        return ResultModel.createSuccess(sysUser);
    }


}
