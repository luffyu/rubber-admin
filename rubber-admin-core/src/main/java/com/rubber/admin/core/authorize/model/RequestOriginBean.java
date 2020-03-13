package com.rubber.admin.core.authorize.model;

import com.rubber.admin.core.authorize.entity.AuthGroupConfig;
import lombok.Data;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-12 15:21
 **/
@Data
public class RequestOriginBean {
    /**
     * 请求的url
     */
    private String url;


    /**
     * 所属于的controller Group
     */
    private AuthGroupConfig controllerGroup;

    /**
     * 所属于的mapping Group
     */
    private AuthGroupConfig mappingGroup;


    /**
     * 最早原始的controllerKey
     */
    private String controllerKey;

    /**
     * 最早原始的mappingKey
     */
    private String mappingKey;



    /**
     * 获取有效的key值
     * @return 当地的key值信息
     */
    public String getEffectiveControllerKey(){
        return controllerGroup == null ? controllerKey : controllerGroup.getGroupKey();
    }

    /**
     * 获取有效的key值
     * @return 当地的key值信息
     */
    public String getEffectiveMappingKey(){
        return mappingGroup == null ? mappingKey : mappingGroup.getGroupKey();
    }


    /**
     * 获取有效的名称值
     * @return 当地的key值信息
     */
    public String getEffectiveControllerName(){
        return controllerGroup == null ? controllerKey : controllerGroup.getGroupName();
    }

    /**
     * 获取有效的名称值
     * @return 当地的key值信息
     */
    public String getEffectiveMappingName(){
        return mappingGroup == null ? mappingKey : mappingGroup.getGroupName();
    }

}
