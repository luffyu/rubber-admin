package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.exception.RoleException;
import com.rubber.admin.core.system.exception.UserException;
import com.rubber.admin.core.system.model.UserInfoModel;
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
     * @param userInfoModel 角色信息
     * @return
     */
    @PostMapping("/add")
    public ResultMsg addUser(UserInfoModel userInfoModel) throws AdminException {
        sysUserService.saveOrUpdateUserInfo(userInfoModel);
        return ResultMsg.success();
    }


    /**
     * 更新角色的信息
     * @param userId 角色id
     * @param userInfoModel 用户的基本信息
     * @return
     */
    @PostMapping("/{userId}/update")
    public ResultMsg updateUser(@PathVariable("userId")Integer userId,UserInfoModel userInfoModel) throws AdminException {
        if(userId == null || userInfoModel.getSysUser() == null || userId <= 0){
            throw new UserException(AdminCode.PARAM_ERROR,"用户id为空");
        }
        if (!userId.equals(userInfoModel.getSysUser().getUserId())){
            throw new UserException(AdminCode.PARAM_ERROR,"用户id不正确");
        }
        sysUserService.saveOrUpdateUserInfo(userInfoModel);
        return ResultMsg.success();
    }


    /**
     * 更新角色的信息
     * @param userId 角色id
     * @return
     */
    @PostMapping("/{userId}/del")
    public ResultMsg delUser(@PathVariable("userId")Integer userId) throws RoleException, UserException {
        if(userId == null || userId <= 0){
            throw new UserException(AdminCode.PARAM_ERROR,"用户id为空");
        }
        sysUserService.delUser(userId);
        return ResultMsg.success();
    }
}
