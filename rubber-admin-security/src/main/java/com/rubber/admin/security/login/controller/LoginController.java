package com.rubber.admin.security.login.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.security.login.bean.LoginBean;
import com.rubber.admin.security.login.service.RubberUserLoginService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@RestController
@RequestMapping
public class LoginController {


    @Resource
    private RubberUserLoginService rubberLoginService;


    @PostMapping("/login")
    public ResultMsg login(LoginBean loginBean, HttpServletRequest request){
        try {
            return rubberLoginService.login(loginBean,request);
        }catch (BadCredentialsException e){
            //密码错误的异常信息
            return ResultMsg.create(AdminCode.LOGIN_AUTH_ERROR);
        }catch (Exception e){
            throw e;
        }
    }


}
