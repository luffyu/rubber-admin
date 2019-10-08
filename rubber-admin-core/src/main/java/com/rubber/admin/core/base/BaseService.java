package com.rubber.admin.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.plugins.page.PageTools;
import com.rubber.admin.core.plugins.select.SelectModel;
import com.rubber.admin.core.plugins.select.SelectTools;
import com.rubber.admin.core.tools.ReflectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author luffyu
 * Created on 2019-05-11
 */
public class BaseService<M extends BaseMapper<T>,T extends BaseEntity> extends ServiceImpl<M,T> implements IBaseService<T> {


    /**
     * 通过多维度到查询列表来查询基本信息
     * @param selectModels 查询列表
     * @param clz 实体的类名称
     * @return
     */
    @Override
    public List<T> listBySelect(List<SelectModel> selectModels,Class<T> clz) {
        QueryWrapper<T> queryWrapper = SelectTools.creatSearchWrapper(selectModels, clz);
        return list(queryWrapper);
    }


    /**
     * 通过多维度分页查询列表的基本信息
     * @param page 分页的page
     * @param selectModels 查询列表信息
     * @param clz 实体的类型
     * @return
     */
    @Override
    public IPage<T> pageBySelect(IPage<T> page, List<SelectModel> selectModels, Class<T> clz) {
        QueryWrapper<T> queryWrapper = SelectTools.creatSearchWrapper(selectModels, clz);
        return page(page,queryWrapper);
    }



    @Override
    public IPage<T> pageBySelect(PageModel pageModel,Class<T> clz) {
        Map<String,Class<?>> clzFiles = ReflectionUtils.getDBEntityFieldsName(clz);
        IPage<T> page = PageTools.build(pageModel,clzFiles);
        QueryWrapper<T> queryWrapper = SelectTools.creatSearchWrapper(pageModel.getSelectModels(), clzFiles);
        return page(page,queryWrapper);
    }


}
