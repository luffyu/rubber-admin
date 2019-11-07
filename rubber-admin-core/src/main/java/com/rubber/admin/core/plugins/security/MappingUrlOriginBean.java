package com.rubber.admin.core.plugins.security;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2019-11-07
 */
@Data
public class MappingUrlOriginBean {

    /**
     * 请求的url
     */
    private String url;

    /**
     * 所在的模块key值
     */
    private String module;


    /**
     * 属于的unit方法key
     */
    private String unit;

    public MappingUrlOriginBean() {
    }

    public MappingUrlOriginBean(String url, String module, String unit) {
        this.url = url;
        this.module = module;
        this.unit = unit;
    }
}
