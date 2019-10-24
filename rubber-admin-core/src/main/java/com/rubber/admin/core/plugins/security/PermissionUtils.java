package com.rubber.admin.core.plugins.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
public class PermissionUtils {


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
     * 拥有查询权限的前缀信息
     */
    private static final String[] LIST_PERMISSION = new String[]{"list","query","get","find","page","info","download","export"};

    /**
     * 拥有添加权限的前缀信息
     */
    private static final String[] ADD_PERMISSION = new String[]{"add","save","install","saving","upload","import"};

    /**
     * 拥有编辑权限的前缀信息
     */
    private static final String[] EDIT_PERMISSION = new String[]{"edit","update","modify","mod"};

    /**
     * 拥有删除权限的前缀信息
     */
    private static final  String[] DEL_PERMISSION = new String[]{"del","delete","remove","rf"};

    /**
     * 审核权限权限
     * 上下架权限
     */
    private static final String[] VERIFY_PERMISSION = new String[]{"verify","online","offline"};


    /**
     * 通过方法名称获取到权限值
     * @param methodName 方法名称
     * @return 返回权限的类型
     */
    public static PermissionEnums getPermission(String methodName){
        if(StrUtil.isNotEmpty(methodName)){
            if(StrUtil.startWithAny(methodName, LIST_PERMISSION)){
                return PermissionEnums.list;
            }
            if(StrUtil.startWithAny(methodName, ADD_PERMISSION)){
                return PermissionEnums.add;
            }
            if(StrUtil.startWithAny(methodName, EDIT_PERMISSION)){
                return PermissionEnums.edit;
            }
            if(StrUtil.startWithAny(methodName, VERIFY_PERMISSION)){
                return PermissionEnums.verify;
            }
            if(StrUtil.startWithAny(methodName, DEL_PERMISSION)){
                return PermissionEnums.delete;
            }
        }
        return PermissionEnums.common;
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
