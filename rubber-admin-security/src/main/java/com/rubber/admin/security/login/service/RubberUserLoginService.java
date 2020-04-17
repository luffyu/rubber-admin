package com.rubber.admin.security.login.service;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.luffyu.util.result.ResultMsg;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.service.ISysUserService;
import com.rubber.admin.security.login.bean.LoginBean;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@Service
public class RubberUserLoginService {


    @Resource
    private ISysUserService sysUserService;


    @Resource
    private AuthenticationManager authenticationManager;



    /**
     * 登陆操作的service
     * @param loginBean 登陆的基本信息
     * @return 登陆操作
     */
    public ResultMsg login(LoginBean loginBean, HttpServletRequest request){
        // 用户验证
        Authentication authentication  = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginBean, loginBean.getPassword()));
        LoginUserDetail principal = (LoginUserDetail)authentication.getPrincipal();
        //登陆之后的操作
        doLoginAfter(loginBean,principal.getSysUser(),request);
        //返回登陆成功的信息
        return ResultMsg.success(principal);
    }


    /**
     * 登陆成功之后的用户信息
     * @param sysUser 用户的基本信息
     */
    private void doLoginAfter(LoginBean loginBean,SysUser sysUser,HttpServletRequest request){
        String clientIp = ServletUtil.getClientIP(request);
        sysUser.setLoginIp(clientIp);
        sysUser.addCount();
        sysUser.setLoginTime(loginBean.getLoginTime());
        sysUserService.updateById(sysUser);
    }

}
