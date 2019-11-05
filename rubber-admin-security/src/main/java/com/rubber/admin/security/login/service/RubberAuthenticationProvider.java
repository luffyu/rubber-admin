package com.rubber.admin.security.login.service;

import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.encrypt.IEncryptHandler;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.security.auth.ITokenVerifyService;
import com.rubber.admin.security.login.bean.LoginBean;
import com.rubber.admin.security.login.bean.LoginException;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import com.rubber.admin.security.login.service.find.IUserFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-10-30
 */
@Component
public class RubberAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private ITokenVerifyService iTokenAuth;

    @Resource
    private IUserFindService iUserFindService;

    @Autowired
    private IEncryptHandler encryptHandler;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        LoginBean loginBean = (LoginBean) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        //找到用户信息
        SysUser userInfo = iUserFindService.findByAccount(loginBean);
        //验证密码是否相同
        if(!encryptHandler.matches(password,userInfo.getSalt(),userInfo.getLoginPwd())){
            throw new LoginException(AdminCode.LOGIN_AUTH_ERROR);
        }
        LoginUserDetail userDetails = new LoginUserDetail(userInfo);
        //创建token
        iTokenAuth.create(userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
