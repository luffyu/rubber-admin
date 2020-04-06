package com.rubber.admin.core.authorize;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-12 16:26
 **/
public class AuthorizeTools extends AuthorizeKeys{

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
     * 生成权限进行
     * @param apply 业务信息
     * @param option 操作信息
     * @return 返回权限的key
     */
    public static String createAuthKey(String apply,String option){
        return apply + AUTH_LINK_KEY + option;
    }


    /**
     * 生成权限进行
     * @param applies 业务信息
     * @param option 操作信息
     * @return 返回权限的key
     */
    public static Set<String> createAuth(String[] applies,String option){
        Set<String> auths = new HashSet<>();
        for (String apply:applies){
            auths.add(createAuthKey(apply,option));
        }
        return auths;
    }

    /**
     * 生成权限进行
     * @param apply 业务信息
     * @param options 操作信息
     * @return 返回权限的key
     */
    public static Set<String> createAuth(String apply,String[] options){
        Set<String> auths = new HashSet<>();
        for (String option:options){
            auths.add(createAuthKey(apply,option));
        }
        return auths;
    }
}
