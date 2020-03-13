package com.rubber.admin.core.authorize.model;

import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.authorize.AuthorizeKeys;
import lombok.Data;

import java.util.List;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-12 18:14
 **/
@Data
public class GroupControllerModel {

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
     * 组成员信息
     */
    private List<GroupMappingModel> mappingModels;


    public GroupControllerModel() {
    }


    public GroupControllerModel(RequestOriginBean mappingOriginBean) {
       this.groupKey = mappingOriginBean.getEffectiveControllerKey();
       this.groupName = mappingOriginBean.getEffectiveControllerName();
       if(mappingOriginBean.getControllerGroup() != null){
           this.members = StrUtil.split(mappingOriginBean.getControllerGroup().getGroupMember(), AuthorizeKeys.MEMBER_LINK_KEY);
       }
    }
}
