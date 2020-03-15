package com.rubber.admin.core.system.model;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *     树形接口对象
 * </p>
 *
 * @author luffyu
 * @date 2020-03-14 17:14
 **/
@Data
public class TreeDataModel {


    /**
     * 唯一key值
     */
    private String key;

    /**
     * 标签值
     */
    private String label;


    /**
     * 子级数据
     */
    private List<TreeDataModel>  children;


    public TreeDataModel() {
    }

    public TreeDataModel(String key, String label) {
        this.key = key;
        this.label = label;
    }
}
