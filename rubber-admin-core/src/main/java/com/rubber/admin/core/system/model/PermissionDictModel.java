package com.rubber.admin.core.system.model;

import com.rubber.admin.core.system.entity.SysPermissionDict;
import lombok.Data;

import java.util.Map;

/**
 * @author luffyu
 * Created on 2019-11-05
 * 权限字段信息
 */
@Data
public class PermissionDictModel {

    /**
     * key值信息
     */
    private String key;

    /**
     * 名称信息
     */
    private String name;

    /**
     * 关联映射的key值信息
     */
    private String linkKey;


    /**
     * 二级权限信息
     */
    private Map<String,PermissionDictModel> unitKey;


    public PermissionDictModel() {
    }


    public PermissionDictModel(String key,SysPermissionDict sysPermissionDict) {
        this.key = key;
        if(sysPermissionDict != null){
            this.key = sysPermissionDict.getDictKey();
            this.name = sysPermissionDict.getDictName();
            this.linkKey = sysPermissionDict.getDictValue();
        }

    }
}
