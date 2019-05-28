package com.rubber.admin.framework.shiro.config;

import com.rubber.admin.framework.shiro.UserAuthRealm;
import com.rubber.admin.framework.shiro.session.RedisCacheSessionDao;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
@Configuration
public class ShiroConfiguration {

    private Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);


    @Autowired
    private ShiroProperties shiroProperties;

    @Autowired(required = false)
    private JedisCluster jedisCluster;


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //shiro的核心安全接口 属性必须设置
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //身份认真失败跳转的指定页面
        shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
        //权限认真失败，指定的跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());
        //成功之后的默认跳转地址
        shiroFilterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());

        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        //对静态资源设置匿名访问

        //退出登陆地址 shiro会清楚session
        filterChainDefinitionMap.put(shiroProperties.getLogOutUrl(),"logout");
        //必须要有admin权限
        filterChainDefinitionMap.put("/sys/**", "roles[admin]");
        //不需要拦截的访问
        if(shiroProperties.getAnonUrl() != null){
            for (String anonUrl:shiroProperties.getAnonUrl()){
                filterChainDefinitionMap.put(anonUrl, "anon");
            }
        }

        //设置拦截器
        Map<String, Filter> filters = new LinkedHashMap<>();
        shiroFilterFactoryBean.setFilters(filters);

        //其他的都需要进行登陆认证才能进行访问
        filterChainDefinitionMap.put("/*", "authc");
        filterChainDefinitionMap.put("/**", "authc");
        filterChainDefinitionMap.put("/*.*", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(@Qualifier("authRealm") UserAuthRealm authRealm) {
        logger.info("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        //设置realm.
        manager.setRealm(authRealm);
        //注入缓存管理器;

        if (shiroProperties.isEnableRedisCache()){
            if(jedisCluster == null){
                throw new RuntimeException("jedisCluster is null");
            }
            //session管理器
            manager.setSessionManager(sessionManager());
        }
        //注入记住我管理器;
        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }


    /**
     * cookie管理对象;
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //设置啥？？
        cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
        logger.info("--------------rememberMeManager init---------------"+cookieRememberMeManager);
        return cookieRememberMeManager;
    }



    /**
     * cookie对象;
     * @return
     * */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //设置cookie的域名 默认为空 表示是当前域名
        simpleCookie.setDomain(shiroProperties.getCookie().getDomain());
        //设置cookie的有效访问路径
        simpleCookie.setPath(shiroProperties.getCookie().getPath());
        //设置HttpOnly属性
        simpleCookie.setHttpOnly(shiroProperties.getCookie().isHttpOnly());
        //设置Cookie的过期时间，秒为单位
        simpleCookie.setMaxAge(shiroProperties.getCookie().getMaxAge());

        logger.info("--------------rememberMeCookie init---------------"+simpleCookie);
        return simpleCookie;
    }


    /**
     * 配置redis的sessionManger
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(getSessionDao());
        return defaultWebSessionManager;
    }



    @Bean
    public RedisCacheSessionDao getSessionDao(){
        return new RedisCacheSessionDao(shiroProperties.getSessionTimeOut());
    }




    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     * shiro生命周期处理器 ？？？？？
     * @return
     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }




    /**
     * AOP式方法级权限检查
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
