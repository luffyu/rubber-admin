package com.rubber.admin.security.user;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.security.user.service.RubberUserLoginService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public ResultMsg login(String username, String password){
        try {
            return rubberLoginService.login(username,password);
        }catch (BadCredentialsException e){
            //密码错误的异常信息
            return ResultMsg.create(AdminCode.LOGIN_AUTH_ERROR);
        }catch (Exception e){
            throw e;
        }
    }


}
