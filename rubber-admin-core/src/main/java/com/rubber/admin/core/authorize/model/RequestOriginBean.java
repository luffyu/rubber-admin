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
    private AuthGroupConfig applyGroup;

    /**
     * 所属于的mapping Group
     */
    private AuthGroupConfig optionGroup;


    /**
     * 最早原始的controllerKey
     */
    private String applyKey;

    /**
     * 最早原始的mappingKey
     */
    private String optionKey;



    /**
     * 获取有效的key值
     * @return 当地的key值信息
     */
    public String getEffectiveApplyKey(){
        return applyGroup == null ? applyKey : applyGroup.getGroupKey();
    }

    /**
     * 获取有效的名称值
     * @return 当地的key值信息
     */
    public String getEffectiveApplyName(){
        return applyGroup == null ? applyKey : applyGroup.getGroupName();
    }

    /**
     * 获取有效的key值
     * @return 当地的key值信息
     */
    public String getEffectiveOptionKey(){
        return optionGroup == null ? optionKey : optionGroup.getGroupKey();
    }


    /**
     * 获取有效的名称值
     * @return 当地的key值信息
     */
    public String getEffectiveOptionName(){
        return optionGroup == null ? optionKey : optionGroup.getGroupName();
    }



    public String getEffectiveOptionMembers(){
        return optionGroup == null ? "":optionGroup.getGroupMember();
    }

    public String getEffectiveApplyMembers(){
        return applyGroup == null ? "":applyGroup.getGroupMember();
    }
}
