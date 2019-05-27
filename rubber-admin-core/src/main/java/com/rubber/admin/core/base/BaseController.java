package com.rubber.admin.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.admin.core.model.PagerModel;
import com.rubber.admin.core.page.CompareModel;
import com.rubber.admin.core.page.CompareTools;
import com.rubber.admin.core.util.ReflectionUtils;
import com.rubber.admin.core.util.StringTools;

import java.util.List;
import java.util.Map;

/**
 * @author luffyu
 * Created on 2019-05-25
 */
public class BaseController {


    /**
     * 解析获取到查询到page信息
     * @param pagerModel 查询信息
     * @param <T> 实体信息
     * @return 返回查询到page
     */
    public <T extends BaseEntity> Page<T> creatPager(PagerModel pagerModel){
        Page<T> page = new Page<>();
        page.setCurrent(pagerModel.getPage());
        page.setSize(pagerModel.getSize());
        return page;
    }



    /**
     * 解析获取到查询到wrapper信息
     * @param entity 实体信息
     *        entity中到字段全部采用驼峰命名，由于mabatis-plus是采用到下划线 所以这里有一个转化
     *        ？？？ 功能有点太单一了
     * @param pagerModel 查询信息
     * @param <T> 实体信息
     * @return 返回查询到QueryWrapper
     */
    public <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(T entity, PagerModel pagerModel){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Map<String,Object> modelMap = ReflectionUtils.getClassFieldsValues(entity);
        if(modelMap != null && modelMap.size() > 0){
            for(Map.Entry<String,Object> map:modelMap.entrySet()){
                queryWrapper.eq(StringTools.underline(map.getKey()),map.getValue());
            }
        }
        if(!StringTools.isEmpty(pagerModel.getOrder())){
            if("desc".equalsIgnoreCase(pagerModel.getOrder())){
                queryWrapper.orderByDesc(StringTools.underline(pagerModel.getSort()));
            }else{
                queryWrapper.orderByAsc(StringTools.underline(pagerModel.getSort()));
            }
        }
        return queryWrapper;
    }



    /**
     * 自动解析创建查询参数信息
     * @param compareModels 查询的model
     * @param pagerModel 分页的model
     * @param clz 类名称
     * @param <T> 泛型
     * @return 返回参数信息
     */
    public <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(List<CompareModel> compareModels, PagerModel pagerModel,Class<T> clz){
        QueryWrapper<T> queryWrapper = CompareTools.creatSearchWrapper(compareModels, clz);
        if(!StringTools.isEmpty(pagerModel.getOrder())){
            if("desc".equalsIgnoreCase(pagerModel.getOrder())){
                queryWrapper.orderByDesc(StringTools.underline(pagerModel.getSort()));
            }else{
                queryWrapper.orderByAsc(StringTools.underline(pagerModel.getSort()));
            }
        }
        return queryWrapper;
    }



}
