package com.rubber.admin.security.login;

import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.service.impl.SysUserServiceImpl;
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

        return new LoginUserDetail(byLoginName);
    }
}
