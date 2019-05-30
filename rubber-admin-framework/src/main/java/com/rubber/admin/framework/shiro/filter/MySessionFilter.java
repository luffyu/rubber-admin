package com.rubber.admin.framework.shiro.filter;

import com.rubber.admin.core.exceptions.base.BaseException;
import com.rubber.admin.framework.shiro.config.ShiroProperties;
import com.rubber.admin.framework.shiro.session.redis.RedisSessionTools;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebFault;
import java.io.IOException;

/**
 * @author luffyu
 * Created on 2019-05-30
 */
@Component
@WebFault(faultBean = "/**")
public class MySessionFilter implements Filter {

    @Autowired
    private RedisSessionTools redisSessionTools;

    @Autowired
    private ShiroProperties shiroProperties;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //如果支持redis缓存的话
        if(shiroProperties.isEnableRedisCache()){
            if (request instanceof HttpServletRequest){
                HttpServletRequest httpServletRequest = (HttpServletRequest)request;
                if(checkIfCheckLogin(httpServletRequest)){
                    String sessionId =httpServletRequest.getRequestedSessionId();
                    if (StringUtils.isEmpty(sessionId)){
                        throw new BaseException("没有登陆 cookie为空");
                    }
                    Session session = redisSessionTools.doGetRedis(sessionId);
                    if(session == null){
                        throw new BaseException("没有登陆 cookie为空");
                    }
                }
            }
        }
        chain.doFilter(request,response);
    }


    private boolean checkIfCheckLogin(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        if(shiroProperties.getLoginUrl().equalsIgnoreCase(requestURI)){
            return false;
        }
        return true;
    }

}
