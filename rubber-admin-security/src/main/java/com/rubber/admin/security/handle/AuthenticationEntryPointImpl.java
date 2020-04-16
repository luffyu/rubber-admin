package com.rubber.admin.security.handle;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.luffyu.util.result.ResultMsg;
import com.alibaba.fastjson.JSON;
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
        String msg = StrUtil.format("没有权限访问：{}，异常信息：{}", request.getRequestURI(),e.getMessage());
        ResultMsg error = ResultMsg.error(msg);
        response.setStatus(200);
        ServletUtil.writeJSON(response, JSON.toJSONString(error));
    }
}
