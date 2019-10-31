package com.rubber.admin.core.plugins.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.system.entity.SysPrivilegeDict;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
public class PrivilegeUtils {

    /**
     * 基础的原始权限
     */
    public static final String BASIC_UNIT = "basic_unit";

    /**
     * 基础的原始权限
     */
    public static final String BASIC_MODULE = "basic_module";

    /**
     * 默认的基础权限
     */
    public static final String DEFAULT_UNIT_KEY = "select";

    /**
     * 默认的模块权限
     */
    public static final String DEFAULT_MODEL_KEY = "common";


    /**
     * 权限链接字符
     */
    public static final String PER_LINK_KEY = ":";

    /**
     * url请求的链接key
     */
    public static final String URL_LINK_KEY = "/";

    /**
     * 超级管理员的权限
     */
    public static final String SUPER_ADMIN_PERMISSION = "*:*";


    /**
     * 生成验证的key
     * @param module
     * @param unit
     * @return
     */
    public static String createAuthorizeKey(String module,String unit){
        return module + PER_LINK_KEY + unit;
    }



    /**
     * 通过配置到字段信息返回一个默认到 unitKey
     * @param methodName 默认到key
     * @param privilegeDicts 数据库中配置到权限字段
     * @return 返回一个合适到权限字段信息
     */
    public static SysPrivilegeDict findByValue(String methodName, List<SysPrivilegeDict> privilegeDicts){
        if(CollectionUtil.isEmpty(privilegeDicts)){
            return null;
        }
        for(SysPrivilegeDict sysPrivilegeDict:privilegeDicts){
            if(StrUtil.startWithAny(methodName,sysPrivilegeDict.getDictCollect())){
                return sysPrivilegeDict;
            }
        }
        return null;
    }


    /**
     * 截取url 的头部字段
     * @param url
     * @return
     */
    public static String getUrlHeadKey(String url){
        if(StringUtils.isEmpty(url)){
            return url;
        }
        if(url.startsWith(URL_LINK_KEY)){
            url = url.substring(1);
        }
        return StrUtil.subBefore(url,URL_LINK_KEY,false);
    }


    /**
     * 是否有权限
     * @param userPermission 用户的全部权限信息
     * @param requestPermission 必须要的权限信息
     * @return
     */
    public static boolean havePermission(Set<String> userPermission,String requestPermission){
        if(CollectionUtil.isEmpty(userPermission)){
            return false;
        }
        return userPermission.contains(SUPER_ADMIN_PERMISSION) || userPermission.contains(requestPermission);
    }
}
