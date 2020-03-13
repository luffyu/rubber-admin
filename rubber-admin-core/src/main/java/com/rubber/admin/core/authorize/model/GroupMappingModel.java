package com.rubber.admin.core.authorize.model;

import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.authorize.AuthorizeKeys;
import lombok.Data;

import java.util.Set;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-12 19:41
 **/
@Data
public class GroupMappingModel {

    /**
     * 组关键字
     */
    private String groupKey;

    /**
     * 组名称
     */
    private String groupName;


    /**
     * 管理的组员信息
     */
    private String[] members;


    /**
     * 包含的url
     */
    private Set<String> urls;



    public GroupMappingModel(RequestOriginBean mappingOriginBean) {
        this.groupKey = mappingOriginBean.getEffectiveControllerKey() + AuthorizeKeys.AUTH_LINK_KEY +mappingOriginBean.getEffectiveMappingKey();
        this.groupName = mappingOriginBean.getEffectiveMappingName();
        if(mappingOriginBean.getMappingGroup() != null){
            this.members = StrUtil.split(mappingOriginBean.getMappingGroup().getGroupMember(), AuthorizeKeys.MEMBER_LINK_KEY);
        }
    }
}
