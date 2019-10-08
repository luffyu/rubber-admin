package com.rubber.admin.core.plugins.page;

import com.rubber.admin.core.plugins.select.SelectModel;
import lombok.Data;

import java.util.List;

/**
 * @author luffyu
 * Created on 2019-05-25
 */
@Data
public class PageModel {

    /**
     * 当前页数
     */
    private int page = 1;

    /**
     * 每一页的大小
     */
    private int size = 10;

    /**
     * 总的页数
     */
    private int total;

    /**
     * 排序字段
     * 支持多个字段排序
     */
    private String[] sort;
    /**
     * 排序方式
     */
    private SortType order;


    /**
     * 查询的条件信息
     */
    private List<SelectModel> selectModels;

}
