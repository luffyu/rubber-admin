package com.rubber.admin.security.controller;

import com.luffyu.piece.utils.result.ResultMsg;
import com.luffyu.piece.utils.result.code.SysCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.MessageUtils;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@RestController
@RequestMapping
public class LoginController {


    @Resource
    private AuthenticationManager authenticationManager;


    @RequestMapping("login")
    public ResultMsg login(String username,String password){

        try {
            // 用户验证
            Authentication authentication  = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (Exception e){
            if (e instanceof BadCredentialsException) {
                return ResultMsg.create(SysCode.PARAM_ERROR);
            }
        }

        return ResultMsg.success();
    }
}
