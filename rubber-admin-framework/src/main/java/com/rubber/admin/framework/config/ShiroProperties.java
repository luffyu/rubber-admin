package com.rubber.admin.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author luffyu
 * Created on 2019-05-21
 */
@Component
@ConfigurationProperties(prefix="shiro")
public class ShiroProperties {

    /**
     * 登陆的url
     */
    private String loginUrl = "/login";

    /**
     * 登出的url
     */
    private String logOutUrl = "/logout";

    /**
     * 权限认证失败的url
     */
    private String unauthorizedUrl = "/auth-error";

    /**
     *  成功之后的默认跳转地址
     */
    private String successUrl = "/default";

    /**
     * 不拦截的url
     */
    private String[] anonUrl = {"//shiro/anon1","","/shiro/anon2"};

    /**
     * 分布式登陆
     */
    private boolean enableRedisCache = false;


    /**
     * session失效时间，当没有操作当时候
     * sessionTimeOut 分钟以后 session会失效
     */
    private int sessionTimeOut = 30;

    /**
     * cookie配置
     */
    private CookieConfig cookie;


    public static class CookieConfig{

        /**
         * 设置Cookie的域名 默认空，即当前访问的域名
         */
        private String domain = "";

        /**
         * 设置cookie的有效访问路径
         */
        private String path = "/";

        /**
         * 设置HttpOnly属性
         */
        private boolean httpOnly = true;

        /**
         * 设置Cookie的过期时间，单位为秒
         */
        private int maxAge;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isHttpOnly() {
            return httpOnly;
        }

        public void setHttpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
        }

        public int getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(int maxAge) {
            this.maxAge = maxAge;
        }
    }


    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogOutUrl() {
        return logOutUrl;
    }

    public void setLogOutUrl(String logOutUrl) {
        this.logOutUrl = logOutUrl;
    }


    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String[] getAnonUrl() {
        return anonUrl;
    }

    public void setAnonUrl(String[] anonUrl) {
        this.anonUrl = anonUrl;
    }

    public CookieConfig getCookie() {
        return cookie;
    }

    public void setCookie(CookieConfig cookie) {
        this.cookie = cookie;
    }


    public boolean isEnableRedisCache() {
        return enableRedisCache;
    }

    public void setEnableRedisCache(boolean enableRedisCache) {
        this.enableRedisCache = enableRedisCache;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }
}
