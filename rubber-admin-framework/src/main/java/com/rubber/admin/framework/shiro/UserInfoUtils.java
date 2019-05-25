package com.rubber.admin.framework.shiro;

import com.rubber.admin.core.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;


/**
 * @author luffyu
 * Created on 2019-05-21
 *
 * 获取用户的基本信息
 */

public class UserInfoUtils {


    /**
     * @return 返回 subject对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }


    /**
     * 获取当前登陆的用户信息
     * @return 返回用户的登陆信息
     */
    public static SysUser getLoginUser(){
        SysUser sysUser = null;
        Object obj = getSubject().getPrincipal();
        if(obj != null){
            sysUser = new SysUser();
            BeanUtils.copyProperties(obj,sysUser);
        }
        return sysUser;
    }
}
