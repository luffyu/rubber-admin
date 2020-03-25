package com.rubber.admin.core.authorize.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-12 15:55
 **/
public enum RubberGroupTypeEnums {

    /**
     * 操作组
     */
    option("操作配置"),

    /**
     * 应用组
     */
    apply("业务配置"),

    ;



    public String label;


    RubberGroupTypeEnums(String label) {
        this.label = label;
    }


    /**
     * 获取业务类型的参数
     * @return 参数信息
     */
    public static Map<String,String> getGroupTypeInfo(){
        Map<String,String> map = new LinkedHashMap<>();
        for (RubberGroupTypeEnums groupEnums: RubberGroupTypeEnums.values()){
            map.put(groupEnums.toString(),groupEnums.label);
        }
        return map;
    }
}
