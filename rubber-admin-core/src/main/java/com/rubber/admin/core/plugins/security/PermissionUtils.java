package com.rubber.admin.core.plugins.security;

import cn.hutool.core.util.StrUtil;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
public class PermissionUtils {

    /**
     * 拥有查询权限的前缀信息
     */
    static final String[] LIST_PERMISSION = new String[]{"list","query","get","find","page","info"};

    /**
     * 拥有添加权限的前缀信息
     */
    static final String[] ADD_PERMISSION = new String[]{"add","save","install","saving"};

    /**
     * 拥有编辑权限的前缀信息
     */
    static final String[] EDIT_PERMISSION = new String[]{"edit","update","modify","mod"};

    /**
     * 拥有删除权限的前缀信息
     */
    static final  String[] DEL_PERMISSION = new String[]{"del","delete","remove","rf"};


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
            if(StrUtil.startWithAny(methodName, DEL_PERMISSION)){
                return PermissionEnums.delete;
            }
        }
        return PermissionEnums.undefined;
    }


}
