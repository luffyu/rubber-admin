package com.rubber.admin.core.plugins.page;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.admin.core.base.BaseEntity;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminRunTimeException;

import java.util.Map;

/**
 * @author luffyu
 * Created on 2019-10-08
 */
public class PageTools {


    /**
     * 创建分页信息
     * @param pagerModel 分页查询的实体信息
     * @param <T> 分页查询
     * @return 返回分页的实体bean
     */
    public static <T extends BaseEntity> IPage<T> build(PageModel pagerModel){
        Page<T> page = new Page<>();
        page.setCurrent(pagerModel.getPage());
        page.setSize(pagerModel.getSize());
        if(pagerModel.getOrder() != null && ArrayUtil.isNotEmpty(pagerModel.getSort())){
            switch (pagerModel.getOrder()){
                case asc:
                    page.setAsc(pagerModel.getSort());
                    break;
                case desc:
                    page.setDesc(pagerModel.getSort());
                    break;
                 default:
            }
        }
        return page;
    }


    /**
     * 检测排序的字段是否合法
     * 实体 T 的全部成员变量
     * @param pagerModel 分页的model信息
     * @param clzFiles 实体的全部成员变量
     * @param <T> 实体类
     * @return
     */
    public static <T extends BaseEntity> IPage<T> build(PageModel pagerModel, Map<String,Class<?>> clzFiles){
        if(pagerModel.getOrder() != null && pagerModel.getSort() != null && clzFiles != null){
            for (String s : pagerModel.getSort()) {
                if(clzFiles.get(s) == null){
                    throw new AdminRunTimeException(AdminCode.LOGIN_AUTH_ERROR,s+"是非法成员变量");
                }
            }
        }
        return build(pagerModel);
    }
}
