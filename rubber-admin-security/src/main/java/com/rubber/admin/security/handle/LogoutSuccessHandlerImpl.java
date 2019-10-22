package com.rubber.admin.security.handle;

import com.luffyu.piece.utils.result.ResultMsg;
import com.luffyu.piece.utils.web.ServletTools;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出登陆的操作类
 * @author luffyu
 * Created on 2019-10-13
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        ResultMsg msg = ResultMsg.success("退出成功");
        ServletTools.printResponse(response, msg);
    }
}
