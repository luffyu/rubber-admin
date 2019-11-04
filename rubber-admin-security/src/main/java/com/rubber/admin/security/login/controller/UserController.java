package com.rubber.admin.security.login.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.system.model.UserInfoModel;
import com.rubber.admin.core.system.service.ISysUserService;
import com.rubber.admin.core.tools.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@RestController
@RequestMapping("/u")
public class UserController {

    @Resource
    private ISysUserService sysUserService;


    /**
     * 获取用户的全部信息接口
     * @return
     */
    @GetMapping("/info")
    public ResultMsg info() throws AdminException {
        Integer userId = ServletUtils.getLoginUserId();
        UserInfoModel loginUserInfo = sysUserService.getUserAllInfo(userId);
        return ResultMsg.success(loginUserInfo);
    }

}
