package com.rubber.admin.core.system.model;

import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.plugins.security.PermissionUtils;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luffyu
 * Created on 2019-10-31
 * 全部的权限信息
 */
@Data
public class PermissionBean implements Serializable {

    /**
     * 模块key
     */
    private String moduleKey;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 具体的权限信息
     */
    private List<UnitBean> unitBeans;


    public PermissionBean() {
    }

    public PermissionBean(String moduleKey, String moduleName) {
        this.moduleKey = moduleKey;
        this.moduleName = moduleName;
        this.unitBeans = new ArrayList<>();
    }


    public PermissionBean(String moduleKey, PermissionDictModel dictModel) {
        this.moduleKey = moduleKey;
        this.moduleName = moduleKey;
        if(dictModel != null ){
            this.moduleName = StrUtil.nullToDefault(dictModel.getName(),moduleKey);
        }
    }

    public PermissionBean(String moduleKey, SysPermissionDict module) {
        this.moduleKey = moduleKey;
        if(module != null){
            this.moduleKey = StrUtil.nullToDefault(module.getDictKey(),moduleKey);
            this.moduleName = StrUtil.nullToDefault(module.getDictName(),moduleKey);
        }else {
            this.moduleName = moduleKey;
        }
        this.unitBeans = new ArrayList<>();
    }


    @Data
    public static class UnitBean implements Serializable{
        /**
         * 单个权限的key
         */
        private String unitKey;

        /**
         * 单个权限key的名称
         */
        private String unitName;

        /**
         * 认证的key
         */
        private String authorizeKey;

        public UnitBean() {
        }

        public UnitBean(String unitKey, String unitName, String authorizeKey) {
            this.unitKey = unitKey;
            this.unitName = unitName;
            this.authorizeKey = authorizeKey;
        }


        public UnitBean(String unitKey, String moduleKey,PermissionDictModel dictModel) {
            this.unitKey = unitKey;
            this.unitName = unitKey;
            if(dictModel !=null && dictModel.getUnitKey() != null){
                PermissionDictModel unitModel = dictModel.getUnitKey().get(unitKey);
                if(unitModel != null){
                    this.unitKey = StrUtil.nullToDefault(unitModel.getKey(),unitKey);
                    this.unitName = StrUtil.nullToDefault(unitModel.getName(),unitKey);
                }
            }
            this.authorizeKey = PermissionUtils.createAuthorizeKey(moduleKey, unitKey);
        }



        public UnitBean(String unitKey, SysPermissionDict unit, String authorizeKey) {
            this.unitKey = unitKey;
            if(unit != null && StrUtil.isNotEmpty(unit.getDictName())){
                this.unitName = unit.getDictName();
            }else {
                this.unitName = unitKey;
            }
            this.authorizeKey = authorizeKey;
        }
    }

}
