package com.rubber.admin.core.tools;

import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-11-01
 */
public class ServletUtils extends ServletUtil {

    /**
     * 登陆用户的Key
     */
    public static final String LOGIN_KEY = "login_user_id";


    private static final String X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * 获取当前线程的http请求
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }



    /**
     * 写入当前的用户登陆信息到http中
     * @param userId 系统的中的用户
     * @param request 请求参数
     */
    public static void writeUserToHttp(Integer userId, HttpServletRequest request){
        request.setAttribute(LOGIN_KEY,userId);
    }




    /**
     * 写入当前的用户登陆信息到http中
     */
    public static Integer getLoginUserId(){
        Object attribute = getRequest().getAttribute(LOGIN_KEY);
        if(attribute == null){
            return null;
        }
        return (Integer) attribute;
    }


    /**
     * 返回登陆的ip
     * @return
     */
    public static String getLoginIp(){
        return getClientIP(getRequest(),X_FORWARDED_FOR);
    }

}
