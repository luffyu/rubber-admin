package com.rubber.admin.core.system.model;

import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.system.entity.SysPrivilegeDict;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luffyu
 * Created on 2019-10-31
 * 全部的权限信息
 */
@Data
public class PrivilegeBean {

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


    public PrivilegeBean(String moduleKey, String moduleName) {
        this.moduleKey = moduleKey;
        this.moduleName = moduleName;
        this.unitBeans = new ArrayList<>();
    }

    public PrivilegeBean(String moduleKey, SysPrivilegeDict module) {
        this.moduleKey = moduleKey;
        if(module != null && StrUtil.isNotEmpty( module.getDictName())){
            this.moduleName = module.getDictName();
        }else {
            this.moduleName = moduleKey;
        }
        this.unitBeans = new ArrayList<>();
    }


    @Data
    public static class UnitBean{
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

        public UnitBean(String unitKey, String unitName, String authorizeKey) {
            this.unitKey = unitKey;
            this.unitName = unitName;
            this.authorizeKey = authorizeKey;
        }

        public UnitBean(String unitKey, SysPrivilegeDict unit,String authorizeKey) {
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
