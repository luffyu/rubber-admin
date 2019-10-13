package com.rubber.admin.security.utils;


import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 客户端工具类
 * 
 * @author ruoyi
 */
public class ServletUtils {
    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @return null
     */
    public static String printResponse(HttpServletResponse response, Object data) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            if(data instanceof String){
                response.getWriter().print(data);
            }else {
                response.getWriter().print(JSON.toJSON(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
