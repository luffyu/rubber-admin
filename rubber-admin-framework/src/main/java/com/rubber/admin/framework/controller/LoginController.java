package com.rubber.admin.framework.controller;

import com.rubber.admin.core.enums.MsgCode;
import com.rubber.admin.core.model.ResultModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
@RestController
public class LoginController {



    @GetMapping("/login")
    public ResultModel login(String username,String password, HttpServletRequest request) {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return new ResultModel(MsgCode.PARAM_ERROR);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            System.out.println(">>>>>开始进入到token");
            subject.login(token);
            return ResultModel.createSuccess("登陆成功");
        } catch (UnknownAccountException ua) {
            ua.printStackTrace();
            return ResultModel.createError(MsgCode.LOGIN_USER_NOT_EXIST);
        } catch (DisabledAccountException de) {
            de.printStackTrace();
            return ResultModel.createError(MsgCode.USER_IS_DISABLE);
        } catch (IncorrectCredentialsException ie){
            ie.printStackTrace();
            return ResultModel.createError(MsgCode.LOGIN_AUTH_ERROR);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultModel.createError(MsgCode.SYS_ERROR);
        }
    }




    @GetMapping("/logouts")
    public ResultModel logout( HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultModel.createSuccess("退出登陆成功");
    }

}
