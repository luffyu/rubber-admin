package com.rubber.admin.core.authorize.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-14 19:42
 **/
@Data
public class GroupOptionApplyTreeModel {

    /**
     * key值
     */
    private String key;

    /**
     * 名称值
     */
    private String label;

    /**
     * 相关请求Url
     */
    private Set<String> requestUrl;


    /**
     * 成员值
     */
    private String members;

    /**
     * 子菜单结构
     */
    private List<GroupOptionApplyTreeModel> children;

}
