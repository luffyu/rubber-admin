package com.rubber.admin.security.login.service;

import com.luffyu.piece.utils.result.ResultMsg;
import com.rubber.admin.security.login.bean.LoginUserDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@Service
public class RubberLoginService {


    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * 登陆操作的service
     * @param username 用户的名称
     * @param password 用户的密码
     * @return 登陆操作
     */
    public ResultMsg login(String username, String password){
        // 用户验证
        Authentication authentication  = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        LoginUserDetail principal = (LoginUserDetail)authentication.getPrincipal();
        return ResultMsg.success(principal);
    }

}
