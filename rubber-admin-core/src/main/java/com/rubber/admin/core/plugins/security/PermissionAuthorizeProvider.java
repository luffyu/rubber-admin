package com.rubber.admin.core.plugins.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import com.rubber.admin.core.system.service.ISysPermissionDictService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@Component
public class PermissionAuthorizeProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * url的权限字典
     * key表示urlPath
     * value表示该url需要的权限字段key
     */
    private static Map<String,String> urlPermissionDict = new ConcurrentHashMap<>(40);

    /**
     * 全部的权限内容
     *
     * key表示模块头
     * value表示 表示该模块有的权限信息
     */
    private static Map<String,Set<String>> allPermission = new ConcurrentHashMap<>(200);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PermissionAuthorizeProvider.applicationContext = applicationContext;
        writeHandlerMappingAuthorize(applicationContext);
    }


    /**
     * 写入权限标示符到内存中
     * @param applicationContext applicationContext
     */
    public void writeHandlerMappingAuthorize(ApplicationContext applicationContext){
        ISysPermissionDictService privilegeDict = applicationContext.getBean(ISysPermissionDictService.class);
        //获取到全部到权限字典
        List<SysPermissionDict> privilegeUnitDicts = privilegeDict.selectByType(PermissionUtils.BASIC_UNIT);
        if (privilegeUnitDicts == null){
            return;
        }
        //获取模块的目录信息
        List<SysPermissionDict> privilegeModuleDicts = privilegeDict.selectByType(PermissionUtils.BASIC_MODULE);

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
                resolveHandlerMethod(urlPath,map.getValue(),privilegeUnitDicts,privilegeModuleDicts);
            }
        }
    }

    /**
     * 解析handlerMethod方法
     * @param urlPath url地址
     * @param handlerMethod 方法地址
     * @param privilegeUnitDicts list信息
     */
    public void resolveHandlerMethod(String urlPath, HandlerMethod handlerMethod, List<SysPermissionDict> privilegeUnitDicts, List<SysPermissionDict> privilegeModuleDicts){
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
            SysPermissionDict moduleDict = PermissionUtils.findByValue(moduleKey, privilegeModuleDicts);
            if(moduleDict != null && moduleDict.getDictKey() != null){
                moduleKey = moduleDict.getDictKey();
            }
        }

        if(privilegeUnitKey == null){
            SysPermissionDict unit = PermissionUtils.findByValue(handlerMethod.getMethod().getName(), privilegeUnitDicts);
            if(unit != null){
                privilegeUnitKey = unit.getDictKey();
            }
            if(StrUtil.isEmpty(privilegeUnitKey)){
                privilegeUnitKey = PermissionUtils.DEFAULT_UNIT_KEY;
            }
        }
        //写入url的权限字段
        writeUrlPermissionDict(urlPath,moduleKey,privilegeUnitKey);
        writeAllPermission(moduleKey,privilegeUnitKey);
    }

    /**
     * 获取mapping的权限key
     * 用 key:Permission 来表示
     * @param urlPath 请求的url
     * @param moduleKey handlerMethod的方法 privilegeUnitKey
     * @return 返回  moduleKey:privilegeUnitKey 接口的key值
     *
     */
    public void writeUrlPermissionDict(String urlPath,String moduleKey,String privilegeUnitKey){
        String authorizeKey = PermissionUtils.createAuthorizeKey(moduleKey,privilegeUnitKey);
        urlPermissionDict.putIfAbsent(urlPath,authorizeKey);
    }



    public synchronized void writeAllPermission(String moduleKey,String unitKey){
        Set<String> privilegeBean = allPermission.get(moduleKey);
        if(privilegeBean == null){
            privilegeBean = new HashSet<>(20);
        }
        privilegeBean.add(unitKey);
        allPermission.put(moduleKey,privilegeBean);
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


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * @return  返回handleMapping中的权限标示
     */
    public static Map<String, String> getMappingAuthorize() {
        return urlPermissionDict;
    }


    /**
     * @return  返回handleMapping中的权限标示
     */
    public static Map<String,Set<String>> getAllAuthorize() {
        return allPermission;
    }
}
