package com.rubber.admin.security.login.service;

import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.service.impl.SysUserServiceImpl;
import com.rubber.admin.security.auth.ITokenVerifyService;
import com.rubber.admin.security.login.bean.LoginException;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@Slf4j
@Component
public class UserDetailServiceImpl implements UserDetailsService {


    @Resource
    private SysUserServiceImpl sysUserService;

    @Resource
    private ITokenVerifyService iTokenAuth;


    /**
     * 具体的登陆验证逻辑
     * @param s 用户的密码信息
     * @return 验证信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser userInfo = sysUserService.getByLoginAccount(s);
        if (userInfo == null){
            //抛出用户的信息
            throw new LoginException(AdminCode.LOGIN_USER_NOT_EXIST,"用户{}不存在", s);
        }else if (userInfo.getStatus() == null || StatusEnums.DISABLE == userInfo.getStatus()){
            //抛出用户的信息
            throw new LoginException(AdminCode.USER_IS_DISABLE,"用户{}被禁用", s);
        }else if(StatusEnums.DELETE == userInfo.getStatus()){
            throw new LoginException(AdminCode.USER_IS_DELETE,"用户{}被删除", s);
        }
        LoginUserDetail loginUserDetail = new LoginUserDetail(userInfo);
        //创建token
        iTokenAuth.create(loginUserDetail);
        return loginUserDetail;
    }
}
