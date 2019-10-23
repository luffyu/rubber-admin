package com.rubber.admin.security.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-10-22
 */
@Data
@Component
@ConfigurationProperties("rubber.security")
public class RubberConfigProperties {


    /**
     * 所有登录和未登录人员都可以访问
     *
     * Key为访问方式
     */
    private Map<HttpMethod,Set<String>> permitAll;

    /**
     * 匿名访问认证
     */
    private Set<String> anonymous = new HashSet<>();

    /**
     * 每一种角色应该有的权限
     */
    private Map<String,Set<String>> rolePatterns;


    /**
     * 登陆的url
     */
    private String logUrl = "/login";


    /**
     * 登出的url
     */
    private String logoutUrl = "/logout";


    /**
     * jwt的tokenKey
     */
    private JwtProperties jwt = new JwtProperties();




    @Data
    public static class JwtProperties{
        /**
         * token的名称
         */
        private String headerKey = "Authorization";

        /**
         * 加密的key
         */
        private String secretKey = "sInVzZXJsZC";

        /**
         * 毫秒单位
         */
        private long timeOut = 10000000;
    }

    public Set<String> getAllAnonymous() {
        anonymous.add(this.logUrl);
        anonymous.add(this.logoutUrl);
        return anonymous;
    }
}
