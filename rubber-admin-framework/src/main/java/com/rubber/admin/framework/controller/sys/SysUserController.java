package com.rubber.admin.framework.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.admin.core.base.BaseAdminController;
import com.rubber.admin.core.entity.SysUser;
import com.rubber.admin.core.model.PagerModel;
import com.rubber.admin.core.model.ResultModel;
import com.rubber.admin.core.page.SelectModel;
import com.rubber.admin.core.service.ISysUserService;
import com.rubber.admin.framework.shiro.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author luffyu
 * Created on 2019-05-21
 */
@RestController
@RequestMapping("/sys/u")
public class SysUserController extends BaseAdminController {

    @Autowired
    private ISysUserService sysUserService;


    /**
     * 查询用户到列表信息
     * @param compareModel 系统到用户信息
     * @param pagerModel 用户到基本信息
     * @return
     */
    @GetMapping("/list")
    public ResultModel pageList(SelectModel compareModel, PagerModel pagerModel){
        Page<SysUser> page = creatPager(pagerModel);
        QueryWrapper<SysUser> queryWrapper = creatSearchWrapper(compareModel,pagerModel,SysUser.class);
        sysUserService.page(page, queryWrapper);
        return ResultModel.createSuccess(page);
    }

    /**
     * 注册用户的信息
     * @param sysUser 用户的基本信息
     */
    @PostMapping("/register")
    public ResultModel register(SysUser sysUser){
        PasswordHelper.encryptPassword(sysUser);
        sysUserService.register(sysUser);
        return ResultModel.createSuccess();
    }


    /**
     * 更新用户的基本信息
     * @param sysUser 用户的基本信息
     */
    @PostMapping("/update")
    public ResultModel update(SysUser sysUser){
        sysUserService.checkAndUpdate(sysUser);
        return ResultModel.createSuccess();
    }


    @PostMapping("/del")
    public ResultModel del(){
        return ResultModel.createSuccess();
    }

}
