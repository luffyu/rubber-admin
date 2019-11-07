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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-11-07
 */
@Component
public class MappingUrlOriginHandler implements ApplicationContextAware {


    /**
     * 解析的最原始的基本信息
     */
    private static List<MappingUrlOriginBean> mappingOriginBeans = new ArrayList<>();



    private static ApplicationContext applicationContext;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MappingUrlOriginHandler.applicationContext = applicationContext;
        writeHandlerMappingAuthorize(applicationContext);
    }


    /**
     * 写入权限标示符到内存中
     * @param applicationContext applicationContext
     */
    public void writeHandlerMappingAuthorize(ApplicationContext applicationContext){
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取到全部到Mapping信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        if(MapUtil.isNotEmpty(handlerMethods)){
            for(Map.Entry<RequestMappingInfo, HandlerMethod> map:handlerMethods.entrySet()){
                //获取到请求到url
                PatternsRequestCondition pc = map.getKey().getPatternsCondition();
                Set<String> pSet = pc.getPatterns();
                if (CollectionUtil.isEmpty(pSet)){
                    continue;
                }
                String urlPath = pSet.iterator().next();
                resolveHandlerMethod(urlPath,map.getValue());
            }
        }
    }


    /**
     * 解析handlerMethod方法
     * @param urlPath url地址
     * @param handlerMethod 方法地址
     */
    public void resolveHandlerMethod(String urlPath, HandlerMethod handlerMethod){
        //模块到key
        String moduleKey = null;
        //权限到基础key
        String privilegeUnitKey = null;
        //获取配置到权限认证字段
        RubberAuthorize methodAnnotation = handlerMethod.getMethodAnnotation(RubberAuthorize.class);
        if(methodAnnotation != null){
            moduleKey = methodAnnotation.moduleKey();
            privilegeUnitKey = methodAnnotation.unitKey();
        }
        //获取默认的handlerMethod
        if(StrUtil.isEmpty(moduleKey)){
            moduleKey = doCreateDefaultKey(handlerMethod,urlPath);
        }
        if(StrUtil.isEmpty(privilegeUnitKey )){
            privilegeUnitKey = handlerMethod.getMethod().getName();
        }
        //写入url的权限字段
        mappingOriginBeans.add(new MappingUrlOriginBean(urlPath,moduleKey,privilegeUnitKey));
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
        return StrUtil.nullToDefault(urlHeadKey, PermissionUtils.DEFAULT_MODEL_KEY);
    }


    public static List<MappingUrlOriginBean> getMappingOriginBeans() {
        return mappingOriginBeans;
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
