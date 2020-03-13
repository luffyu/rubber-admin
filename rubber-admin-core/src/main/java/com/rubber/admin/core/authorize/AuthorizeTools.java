package com.rubber.admin.core.authorize;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

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



    public static String createAuthKey(String controllerKey,String mappingKey){
        return controllerKey + AUTH_LINK_KEY + mappingKey;
    }
}
