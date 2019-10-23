package com.rubber.admin.core.plugins.security;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
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



    public void writeHandlerMappingAuthorize(ApplicationContext applicationContext){
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        if(MapUtil.isNotEmpty(handlerMethods)){
            mappingAuthorize = new HashMap<>(handlerMethods.size());
            for(Map.Entry<RequestMappingInfo, HandlerMethod> map:handlerMethods.entrySet()){
                PatternsRequestCondition pc = map.getKey().getPatternsCondition();
                //获取到请求到url
                Set<String> pSet = pc.getPatterns();
                System.out.println(pSet);
                //在获取方法到标签
                String mappingMethodAuthorizeKey = getMappingMethodAuthorizeKey(map.getValue());

                System.out.println(mappingMethodAuthorizeKey);

            }
        }

    }



    private String getMappingMethodAuthorizeKey(HandlerMethod handlerMethod){
        String id = null;
        PermissionEnums permission = null;
        RubberAuthorize methodAnnotation = handlerMethod.getMethodAnnotation(RubberAuthorize.class);
        if(methodAnnotation != null){
            id= methodAnnotation.id();
            permission = methodAnnotation.permission();
        }
        if(StrUtil.isEmpty(id)){
            //获取默认的
        }
        if(permission == null){
            permission = PermissionUtils.getPermission(handlerMethod.getMethod().getName());
        }
        return id + ":" + permission.toString();
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }




}
