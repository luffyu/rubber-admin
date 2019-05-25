package com.rubber.admin.core.enums;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public enum StatusEnums {

    /**
     * 正常可用的key值
     */
    NORMAL(0,"正常/可用"),

    DISABLE(1,"停用/废弃/删除")
    ;

    StatusEnums(int key, String remark) {
        this.key = key;
        this.remark = remark;
    }

    public int key;

    private String remark;
}
