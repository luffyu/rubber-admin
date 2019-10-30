package com.rubber.admin.security.user.service;

import com.rubber.admin.core.system.entity.SysMenu;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.model.SysUserModel;
import com.rubber.admin.core.system.service.impl.SysMenuServiceImpl;
import com.rubber.admin.security.user.bean.LoginUserDetail;
import com.rubber.admin.security.user.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@Slf4j
@Service
public class RubberUserService {


    @Resource
    private SysMenuServiceImpl sysMenuService;


    /**
     * 获取用户的登陆信息
     * @param request http的请求
     * @return 返回用户的基本信息
     */
    public UserInfo getLoginUserInfo(HttpServletRequest request){
        LoginUserDetail loginUserDetail = LoginUserDetail.getByHttp(request);
        SysUser sysUser = loginUserDetail.getSysUser();


        SysMenu menu = sysMenuService.findMenuByUserId(loginUserDetail.getUserId());
        return new UserInfo(new SysUserModel(sysUser),menu);
    }


}
