package com.rubber.admin.security.handle;

import com.luffyu.piece.utils.StringTools;
import com.luffyu.piece.utils.result.ResultMsg;
import com.rubber.admin.security.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 认证失败的操作类
 * @author luffyu
 * Created on 2019-10-13
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e){
        String msg = StringTools.arrayFormat("没有权限访问：{}", request.getRequestURI());
        ResultMsg error = ResultMsg.error(msg);
        ServletUtils.printResponse(response,error);
    }
}
