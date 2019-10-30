package com.rubber.admin.security.config.properties;

import cn.hutool.core.collection.CollectionUtil;
import com.rubber.admin.core.plugins.security.HandlerMappingAuthorize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2019-10-24
 */
@Slf4j
@Component
public class RubberPropertiesUtils {


    @Resource
    private RubbeSecurityProperties rubbeSecurityProperties;

    /**
     * rubber的配置信息
     */
    private static RubbeSecurityProperties securityProperties;

    /**
     * 配置的不需要进行验证的url
     */
    private static OrRequestMatcher unFilterRequest;


    @PostConstruct
    public void init() {
        securityProperties = this.rubbeSecurityProperties;
        if(securityProperties != null){
            initRequestMatcher();
        }
    }

    /**
     * @return 返回Application的基本信息
     */
    public static ApplicationContext getApplicationContext() {
        return HandlerMappingAuthorize.getApplicationContext();
    }


    /**
     * @return 返回配置信息
     */
    public static RubbeSecurityProperties getSecurityProperties() {
        return securityProperties;
    }



    /**
     * 对不需要授权的接口进行验证
     */
    private static void initRequestMatcher(){
        Set<String> anonymous = securityProperties.getAllAnonymous();
        List<RequestMatcher> matchers =anonymous.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        unFilterRequest = new OrRequestMatcher(matchers);
    }


    /**
     * 验证是否在不验证的url中
     * @param request 当前的请求
     * @return 返回true标示该请求在不需要验证的url中
     */
    public static boolean verifyNotFilter(HttpServletRequest request){
        if(unFilterRequest == null){
            log.error("不过滤的url请求为空");
            return false;
        }
        return unFilterRequest.matches(request);
    }
}
