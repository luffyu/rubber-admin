package com.rubber.admin.core.plugins.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
public class PermissionUtils {

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
    public static final int SUPER_ADMIN_FLAG = 1;


    /**
     * 单元权限链接字符
     */
    public static final String UNIT_LINK_KEY = ",";

    /**
     * url中的版本字段
     */
    public static final String URL_VERION_REGEX = "^(v[0-9])";


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
    public static SysPermissionDict findByValue(String methodName, List<SysPermissionDict> privilegeDicts){
        if(CollectionUtil.isEmpty(privilegeDicts)){
            return null;
        }
        for(SysPermissionDict sysPrivilegeDict:privilegeDicts){
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
        if(ReUtil.contains(URL_VERION_REGEX,url)){
            url = StrUtil.subAfter(url, URL_LINK_KEY, false);
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
        return userPermission.contains(requestPermission);
    }


}
