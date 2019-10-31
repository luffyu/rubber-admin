package com.rubber.admin.core.plugins.security;

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
     *
     */
    private List<UnitBean> unitBeans;


    public PrivilegeBean(String moduleKey, String moduleName) {
        this.moduleKey = moduleKey;
        this.moduleName = moduleName;
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
    }

}
