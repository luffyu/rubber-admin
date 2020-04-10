package com.rubber.admin.core.system.controller;

import cn.hutool.luffyu.util.result.ResultMsg;
import com.rubber.admin.core.base.BaseAdminController;
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
public class SysUserController extends BaseAdminController {

    @Resource
    private ISysUserService sysUserService;

    /**
     * 角色的分页查询
     * @param json 分页查询组件 的json字符串
     * @param request 请求参数
     * @return 返回
     */
    @GetMapping("/list")
    public ResultMsg list(String json, HttpServletRequest request){
        PageModel pageModel = decodeForJsonString(json);
        return ResultMsg.success(sysUserService.pageBySelect(pageModel, SysUser.class, null));
    }



    /**
     * 角色的保存信息
     * @param userInfoModel 角色信息
     * @return
     */
    @PostMapping("/add")
    public ResultMsg addUser(@RequestBody UserInfoModel userInfoModel) throws AdminException {
        sysUserService.saveOrUpdateUserInfo(userInfoModel);
        return ResultMsg.success();
    }


    /**
     * 更新角色的信息
     * @param userId 角色id
     * @param userInfoModel 用户的基本信息
     * @return
     */
    @PostMapping("/update/{userId}")
    public ResultMsg updateUser(@PathVariable("userId")Integer userId,@RequestBody UserInfoModel userInfoModel) throws AdminException {
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
     * 删除用户的全部信息
     * @param userId 角色id
     * @return
     */
    @PostMapping("/del/{userId}")
    public ResultMsg delUser(@PathVariable("userId")Integer userId) throws RoleException, UserException {
        if(userId == null || userId <= 0){
            throw new UserException(AdminCode.PARAM_ERROR,"用户id为空");
        }
        sysUserService.delUser(userId);
        return ResultMsg.success();
    }



    /**
     * 获取用户的全部信息
     * @param userId 角色id
     * @return
     */
    @GetMapping("/{userId}/info")
    public ResultMsg getUser(@PathVariable("userId")Integer userId) throws AdminException {
        if(userId == null || userId <= 0){
            throw new UserException(AdminCode.PARAM_ERROR,"用户id为空");
        }
        UserInfoModel userInfo = sysUserService.getUserInfo(userId);
        return ResultMsg.success(userInfo);
    }
}
