package com.rubber.admin.core.plugins.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@Component
public class HandlerMappingAuthorize implements ApplicationContextAware {

    static ApplicationContext applicationContext;

    static Map<String,String> mappingAuthorize;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        HandlerMappingAuthorize.applicationContext = applicationContext;
        writeHandlerMappingAuthorize(applicationContext);
    }


    /**
     * 写入权限标示符到内存中
     * @param applicationContext applicationContext
     */
    public void writeHandlerMappingAuthorize(ApplicationContext applicationContext){
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        if(MapUtil.isNotEmpty(handlerMethods)){
            mappingAuthorize = new HashMap<>(handlerMethods.size());
            for(Map.Entry<RequestMappingInfo, HandlerMethod> map:handlerMethods.entrySet()){
                PatternsRequestCondition pc = map.getKey().getPatternsCondition();
                //获取到请求到url
                Set<String> pSet = pc.getPatterns();
                if (CollectionUtil.isEmpty(pSet)){
                    continue;
                }
                String urlPath = pSet.iterator().next();
                //在获取方法到标签
                String authorizeKey = getMappingMethodAuthorizeKey(urlPath,map.getValue());
                mappingAuthorize.putIfAbsent(urlPath,authorizeKey);
            }
        }
    }

    /**
     * 获取mapping的权限key
     * 用 key:Permission 来表示
     * @param url 请求的url
     * @param handlerMethod handlerMethod的方法
     * @return 返回  key:Permission 接口的key值
     *
     */
    private String getMappingMethodAuthorizeKey(String url,HandlerMethod handlerMethod){
        String key = null;
        PermissionEnums permission = null;
        RubberAuthorize methodAnnotation = handlerMethod.getMethodAnnotation(RubberAuthorize.class);
        if(methodAnnotation != null){
            key= methodAnnotation.key();
            permission = methodAnnotation.permission();
        }
        if(StrUtil.isEmpty(key)){
            key = doCreateDefaultKey(handlerMethod,url);
        }
        if(permission == null){
            permission = PermissionUtils.getPermission(handlerMethod.getMethod().getName());
        }
        return key + PermissionUtils.PER_LINK_KEY + permission.toString();
    }


    /**
     * 获取默认的 权限key
     * @param handlerMethod handlerMethod的方法
     * @param url 当前handleMapping请求的url
     *             如果 RequestMapping没有设置name熟悉 则默认去请求的 前半部分值
     * @return 返回key只
     */
    private String doCreateDefaultKey(HandlerMethod handlerMethod,String url){
        Class<?> beanType = handlerMethod.getBeanType();
        RequestMapping annotation = beanType.getAnnotation(RequestMapping.class);
        if(annotation != null){
            String name = annotation.name();
            if(StrUtil.isNotEmpty(name)){
                return name;
            }
        }
        String urlHeadKey = PermissionUtils.getUrlHeadKey(url);
        return StrUtil.nullToDefault(urlHeadKey,"*");
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * @return  返回handleMapping中的权限标示
     */
    public static Map<String, String> getMappingAuthorize() {
        return mappingAuthorize;
    }
}
