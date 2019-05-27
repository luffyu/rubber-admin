package com.rubber.admin.core.model;

/**
 * @author luffyu
 * Created on 2019-05-25
 */
public class PagerModel {

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
     */
    private String sort;
    /**
     * 排序方式
     */
    private String order;

    private String field;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
