package com.rubber.admin.security.user.service;

import com.luffyu.piece.utils.result.ResultMsg;
import com.rubber.admin.security.user.bean.LoginUserDetail;
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


    /**
     * 用户的信息注册
     * @param request
     */
    public void register(HttpServletRequest request){
    }



}
