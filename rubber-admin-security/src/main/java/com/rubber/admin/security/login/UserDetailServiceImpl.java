package com.rubber.admin.security.login;

import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.service.impl.SysUserServiceImpl;
import com.rubber.admin.security.auth.jwt.JwtTokenAuthHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private JwtTokenAuthHandle jwtTokenAuthHandle;

    /**
     * 具体的登陆验证逻辑
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser byLoginName = sysUserService.getByLoginName(s);
        //其他的验证信息
        //验证是不是成功
        LoginUserDetail loginUserDetail = new LoginUserDetail(s);
        loginUserDetail.setPassword("$2a$10$z78yiN0qOS8IyN4tE5617eXCCjNmZNZSabgB201hVDNUOJdC8WMEu");

        jwtTokenAuthHandle.creatJwtToken(loginUserDetail);
        return loginUserDetail;
    }
}
