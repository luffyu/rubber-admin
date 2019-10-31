package com.rubber.admin.security.login.service;

import cn.hutool.core.util.RandomUtil;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        LoginBean loginBean = (LoginBean) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        //找到用户信息
        SysUser userInfo = iUserFindService.findByAccount(loginBean);
        if (userInfo == null){
            //抛出用户的信息
            throw new LoginException(AdminCode.LOGIN_USER_NOT_EXIST,"用户{}不存在", loginBean.getAccount());
        }else if (userInfo.getStatus() == null || StatusEnums.DISABLE == userInfo.getStatus()){
            //抛出用户的信息
            throw new LoginException(AdminCode.USER_IS_DISABLE,"用户{}被禁用", loginBean.getAccount());
        }else if(StatusEnums.DELETE == userInfo.getStatus()){
            throw new LoginException(AdminCode.USER_IS_DELETE,"用户{}被删除", loginBean.getAccount());
        }
        LoginUserDetail userDetails = new LoginUserDetail(userInfo);
        //验证密码是否相同
        if(!passwordEncoder.matches(userInfo.getEncodeKey(password),userDetails.getPassword())){
            throw new LoginException(AdminCode.LOGIN_AUTH_ERROR);
        }
        //创建token
        iTokenAuth.create(userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String s = RandomUtil.randomNumbers(6);
        System.out.println(s);

        String encode = bCryptPasswordEncoder.encode("123456" + "798835");
        System.out.println(encode);
    }
}
