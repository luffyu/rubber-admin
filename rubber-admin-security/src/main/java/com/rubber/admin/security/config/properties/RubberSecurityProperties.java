package com.rubber.admin.security.config.properties;

import cn.hutool.core.collection.CollectionUtil;
import com.rubber.admin.security.auth.AuthType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
public class RubberSecurityProperties {


    /**
     * 所有登录和未登录人员都可以访问
     *
     * Key为访问方式
     */
    private Set<String> permitAll = new HashSet<>(10);

    /**
     * 匿名访问认证
     * 不登陆也不用选择 也不用验证权限信息
     */
    private Set<String> anonymous = new HashSet<>(10);

    /**
     * 每一种角色应该有的权限
     */
    private Map<String,Set<String>> rolePatterns;

    /**
     * 是否开启接口认证
     */
    private boolean openInterfaceAuth = true;


    /**
     * 放行静态资源
     */
    private String ignoring;


    /**
     * 登陆的url
     */
    private String logUrl = "/login";


    /**
     * 登出的url
     */
    private String logoutUrl = "/logout";


    /**
     * 默认的权限列表信息
     */
    private Set<String> defaultPermissionUrl = new HashSet<>(10);


    /**
     * jwt的tokenKey
     */
    private JwtProperties jwt = new JwtProperties();


    /**
     * token的有效期
     */
    private long sessionTime = 10000000;

    /**
     * 默认的认证方法
     */
    private AuthType authType = AuthType.jwt;


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

    /**
     * 获取全部不需要登陆验证的url
     * @return 返回 不需要登陆就可以直接验证的url
     */
    public Set<String> getAllAnonymous() {
        Set<String> allAnonymous = new HashSet<>(this.anonymous);
        if(CollectionUtil.isNotEmpty(permitAll)){
            allAnonymous.addAll(permitAll);
        }
        allAnonymous.add(this.logUrl);
        allAnonymous.add(this.logoutUrl);
        return allAnonymous;
    }


    /**
     * 获取全部的用户默认访问的url
     * @return 返回默认的权限url
     */
    public Set<String> getAllDefaultPermissionUrl() {
        Set<String> defaultPermissionUrl = getAllAnonymous();
        defaultPermissionUrl.addAll(this.defaultPermissionUrl);
        defaultPermissionUrl.add("/u/**");
        return defaultPermissionUrl;
    }

}
