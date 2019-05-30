package com.rubber.admin.framework.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.rubber.admin.core.enums.MsgCode;
import com.rubber.admin.core.model.ResultModel;
import com.rubber.admin.framework.shiro.config.ShiroProperties;
import com.rubber.admin.framework.shiro.session.redis.RedisSessionTools;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebFault;
import java.io.IOException;

/**
 * @author luffyu
 * Created on 2019-05-30
 *
 * 除去非登陆的验证信息
 * 防止在分布式登陆session的情况下 另外一个节点获取信息，导致subject不可信的问题
 */
@Component
@WebFault
@Order(value = 2)
public class MySessionLoginFilter implements Filter {

    @Autowired
    private RedisSessionTools redisSessionTools;

    @Autowired
    private ShiroProperties shiroProperties;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //是否登陆
        boolean notLogin = false;
        //如果支持redis缓存的话
        if(shiroProperties.isEnableRedisCache()){
            if (request instanceof HttpServletRequest){
                HttpServletRequest httpServletRequest = (HttpServletRequest)request;
                if(checkIfCheckLogin(httpServletRequest)){
                    String sessionId =httpServletRequest.getRequestedSessionId();
                    if (StringUtils.isEmpty(sessionId)){
                        notLogin = true;
                    }
                    Session session = redisSessionTools.doGetRedis(sessionId);
                    if(session == null){
                       notLogin = true;
                    }
                }
            }
        }
        if(notLogin){
            notLoginOption(response);
            return;
        }
        chain.doFilter(request,response);
    }


    /**
     * 检测该请求是否需要验证session
     * 例如 登陆不需要验证cookie
     */
    private boolean checkIfCheckLogin(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        if(shiroProperties.getLoginUrl().equalsIgnoreCase(requestURI)){
            return false;
        }
        return true;
    }

    /**
     * 不登陆的操作
     */
    private void notLoginOption(ServletResponse response) throws IOException {
        ResultModel resultModel =  ResultModel.createError(MsgCode.USER_NOT_LOGIN);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(JSON.toJSONString(resultModel));
    }

}
