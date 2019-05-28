package com.rubber.admin.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.admin.core.model.PagerModel;
import com.rubber.admin.core.page.SelectModel;
import com.rubber.admin.core.page.SelectTools;
import com.rubber.admin.core.util.StringTools;

import java.util.List;

/**
 * @author luffyu
 * Created on 2019-05-25
 */
public class BaseAdminController {

    /**
     * 解析获取到查询到page信息
     *
     * @param pagerModel 查询信息
     * @param <T>        实体信息
     * @return 返回查询到page
     */
    public <T extends BaseEntity> Page<T> creatPager(PagerModel pagerModel) {
        Page<T> page = new Page<>();
        page.setCurrent(pagerModel.getPage());
        page.setSize(pagerModel.getSize());
        return page;
    }


    /**
     * 自动解析创建查询参数信息
     *
     * @param selectModels 查询的model
     * @param pagerModel    分页的model
     * @param clz           类名称
     * @param <T>           泛型
     * @return 返回参数信息
     */
    public <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(List<SelectModel> selectModels, PagerModel pagerModel, Class<T> clz) {
        QueryWrapper<T> queryWrapper = SelectTools.creatSearchWrapper(selectModels, clz);
        return createOrderWrapper(pagerModel, queryWrapper);
    }


    public <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(SelectModel  selectModel, PagerModel pagerModel, Class<T> clz) {
        QueryWrapper<T> queryWrapper = SelectTools.creatSearchWrapper(selectModel, clz);
        return createOrderWrapper(pagerModel, queryWrapper);
    }


    /**
     * 加入排序的方法
     */
    private <T extends BaseEntity> QueryWrapper<T> createOrderWrapper(PagerModel pagerModel, QueryWrapper<T> queryWrapper) {
        if (!StringTools.isEmpty(pagerModel.getOrder())) {
            if (SqlKeyword.DESC.getSqlSegment().equalsIgnoreCase(pagerModel.getOrder())) {
                queryWrapper.orderByDesc(StringTools.underline(pagerModel.getSort()));
            } else {
                queryWrapper.orderByAsc(StringTools.underline(pagerModel.getSort()));
            }
        }
        return queryWrapper;
    }


}
