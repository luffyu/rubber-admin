package com.rubber.admin.core.authorize;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.authorize.model.RequestOriginBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
 * <p>
 *     权限mapping的提供者
 *     通过Application的上下文 获取到 当前容器中的全部的Url
 *
 *     是最原始的基础数据
 * </p>
 *
 * @author luffyu
 * @date 2020-03-12 14:47
 **/
@Component
public class RequestOriginProvider implements ApplicationContextAware {

    /**
     * 全部数据结构
     */
    private static List<RequestOriginBean> requestOriginBeans = new ArrayList<>();


    public static List<RequestOriginBean> getRequestOriginBeans() {
        return requestOriginBeans;
    }

    private static ApplicationContext applicationContext;


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RequestOriginProvider.applicationContext = applicationContext;
        //初始化记载bean
        initRequestOriginBean(applicationContext);
    }



    /**
     * 初始化当前的MappingBean方法
     * @param applicationContext spring上下文
     */
    private void initRequestOriginBean(ApplicationContext applicationContext){
        //获取全部的RequestMapping映射器
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
                //处理生成requestOriginBean
                RequestOriginBean requestOriginBean = new RequestOriginBean();
                requestOriginBean.setUrl(pSet.iterator().next());
                handleOriginAuthKey(requestOriginBean,map.getValue());
                //数据写入
                doWriteBean(requestOriginBean);
            }
        }
    }

    /**
     * 写入到内存中
     * @param bean 当前的bean
     */
    private void doWriteBean(RequestOriginBean bean){
        requestOriginBeans.add(bean);
    }



    /**
     * 处理mapping中的key值
     */
    private void handleOriginAuthKey(RequestOriginBean requestOriginBean, HandlerMethod handlerMethod){
        String applyKey= null,optionKey= null;
        RubberAuth rubberAuth = handlerMethod.getMethodAnnotation(RubberAuth.class);
        if(rubberAuth != null){
            applyKey = rubberAuth.controllerKey();
            optionKey = rubberAuth.mappingKey();
        }
        if (StringUtils.isEmpty(applyKey)){
            applyKey = doCreateOriginControllerKey(handlerMethod,requestOriginBean.getUrl());
        }
        if (StringUtils.isEmpty(optionKey)){
            optionKey = handlerMethod.getMethod().getName();
        }
        requestOriginBean.setApplyKey(applyKey);
        requestOriginBean.setOptionKey(optionKey);
    }



    /**
     * 获取默认的 权限key
     * @param handlerMethod handlerMethod的方法
     * @param url 当前handleMapping请求的url
     *             如果 RequestMapping没有设置name熟悉 则默认去请求的 前半部分值
     * @return 返回key只
     */
    private String doCreateOriginControllerKey(HandlerMethod handlerMethod,String url){
        Class<?> beanType = handlerMethod.getBeanType();
        RequestMapping annotation = beanType.getAnnotation(RequestMapping.class);
        if(annotation != null){
            String name = annotation.name();
            if(StrUtil.isNotEmpty(name)){
                return name;
            }
        }
        String urlHeadKey = AuthorizeTools.getUrlHeadKey(url);
        return StrUtil.nullToDefault(urlHeadKey, AuthorizeTools.DEFAULT_MODEL_KEY);
    }

}
