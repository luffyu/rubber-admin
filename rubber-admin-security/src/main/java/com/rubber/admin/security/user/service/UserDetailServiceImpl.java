package com.rubber.admin.security.user.service;

import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.enums.StatusEnums;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.service.impl.SysUserServiceImpl;
import com.rubber.admin.security.auth.jwt.JwtTokenAuthHandle;
import com.rubber.admin.security.user.bean.LoginException;
import com.rubber.admin.security.user.bean.LoginUserDetail;
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
    private JwtTokenAuthHandle jwtTokenAuthHandle;

    /**
     * 具体的登陆验证逻辑
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser userInfo = sysUserService.getByLoginName(s);
        if (userInfo == null){
            //抛出用户的信息
            String msg = StrUtil.format("用户{}不存在", s);
            log.info(msg);
            throw new LoginException(AdminCode.LOGIN_USER_NOT_EXIST,msg);
        }else if (userInfo.getStatus() == null || StatusEnums.DISABLE == userInfo.getStatus()){
            //抛出用户的信息
            String msg = StrUtil.format("用户{}被禁用", s);
            log.info(msg);
            throw new LoginException(AdminCode.USER_IS_DISABLE,msg);
        }else if(StatusEnums.DELETE == userInfo.getStatus()){
            String msg = StrUtil.format("用户{}被删除", s);
            log.info(msg);
            throw new LoginException(AdminCode.USER_IS_DELETE,msg);
        }
        LoginUserDetail loginUserDetail = new LoginUserDetail(userInfo);
        //创建token
        jwtTokenAuthHandle.creatJwtToken(loginUserDetail);
        return loginUserDetail;
    }
}
