package com.rubber.admin.security.config.properties;

import com.rubber.admin.core.authorize.RequestOriginProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private RubberSecurityProperties rubbeSecurityProperties;

    /**
     * rubber的配置信息
     */
    private static RubberSecurityProperties securityProperties;

    /**
     * 配置的不需要进行验证的url
     */
    private static OrRequestMatcher unLoginFilterRequest;



    private static OrRequestMatcher unPermissionFilterRequest;



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
        return RequestOriginProvider.getApplicationContext();
    }


    /**
     * @return 返回配置信息
     */
    public static RubberSecurityProperties getSecurityProperties() {
        return securityProperties;
    }



    /**
     * 对不需要授权的接口进行验证
     */
    private static void initRequestMatcher(){
        Set<String> anonymous = securityProperties.getAllAnonymous();
        List<RequestMatcher> matchers =anonymous.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        unLoginFilterRequest = new OrRequestMatcher(matchers);


        Set<String> defaultPermission = securityProperties.getAllDefaultPermissionUrl();
        List<RequestMatcher> defaultPermissionMatchers =defaultPermission.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        unPermissionFilterRequest = new OrRequestMatcher(defaultPermissionMatchers);
    }


    /**
     * 验证是否在不验证的url中
     * @param request 当前的请求
     * @return 返回true标示该请求在不需要验证的url中
     */
    public static boolean verifyNotLoginFilter(HttpServletRequest request){
        if(unLoginFilterRequest == null){
            log.error("不过滤的url请求为空");
            return false;
        }
        return unLoginFilterRequest.matches(request);
    }


    /**
     * 验证是否在不验证的url中
     * @param request 当前的请求
     * @return 返回true标示该请求在不需要验证的url中
     */
    public static boolean verifyNotPermessionFilter(HttpServletRequest request){
        if(unPermissionFilterRequest == null){
            log.error("不过滤的url请求为空");
            return false;
        }
        return unPermissionFilterRequest.matches(request);
    }
}
