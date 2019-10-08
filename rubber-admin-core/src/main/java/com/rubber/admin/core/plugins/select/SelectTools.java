package com.rubber.admin.core.plugins.select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luffyu.piece.utils.StringTools;
import com.rubber.admin.core.base.BaseEntity;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.tools.ReflectionUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author luffyu
 * Created on 2019-05-27
 */
public class SelectTools {

    /**
     * 通过传入的参数信息来比较对象信息
     * @param selectModels 参数信息
     * @param clz 对象信息
     * @param <T> entit的类名称
     * @return 返回查询的参数
     */
    public static  <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(List<SelectModel> selectModels, Class<T> clz) throws AdminException {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if(!CollectionUtils.isEmpty(selectModels)){
            Map<String,Class<?>> clzFiles = ReflectionUtils.getDBEntityFieldsName(clz);
            for(SelectModel compareModel:selectModels){
                creatSearchWrapper(queryWrapper,clzFiles,compareModel);
            }
        }
        return queryWrapper;
    }

    /**
     * 通过传入的参数信息来比较对象信息
     * @param selectModels 参数信息
     * @param <T> entit的类名称
     * @return 返回查询的参数
     */
    public static  <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(List<SelectModel> selectModels,  Map<String,Class<?>> clzFiles) throws AdminException {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if(!CollectionUtils.isEmpty(selectModels)){
            for(SelectModel compareModel:selectModels){
                creatSearchWrapper(queryWrapper,clzFiles,compareModel);
            }
        }
        return queryWrapper;
    }



    /**
     * 传入单个参数查询用户的基本信息
     * @param selectModel 查询的参数
     * @param clz 实体类
     * @param <T> 泛型
     * @return 返回查询的queryWrapper
     */
    public static  <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(SelectModel selectModel, Class<T> clz) throws AdminException {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Map<String,Class<?>> clzFiles = ReflectionUtils.getDBEntityFieldsName(clz);
        creatSearchWrapper(queryWrapper,clzFiles,selectModel);
        return queryWrapper;
    }



    /**
     * 通过单个参数查询参数信息
     * @param queryWrapper
     * @param clzFiles
     * @param selectModel
     * @param <T>
     * @return
     */
    private static  <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(QueryWrapper<T> queryWrapper, Map<String,Class<?>> clzFiles, SelectModel selectModel) throws AdminException {
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }
        if(StringUtils.isEmpty(selectModel.getField())){
            return queryWrapper;
        }
        if(!clzFiles.containsKey(selectModel.getField())){
            throw new AdminException(AdminCode.LOGIN_AUTH_ERROR,selectModel.getField()+"是非法成员变量");
        }
        String column = StringTools.underline(selectModel.getField());
        Class<?> aClass = clzFiles.get(selectModel.getField());
        //解析比较信息
        switch (selectModel.getType()){
            case eq:
                queryWrapper.eq(column,selectModel.getData());
                break;
            case ne:
                queryWrapper.ne(column,selectModel.getData());
                break;
            case gt:
                queryWrapper.gt(column,selectModel.getData());
                break;
            case ge:
                queryWrapper.gt(column,selectModel.getData());
                break;
            case lt:
                queryWrapper.lt(column,selectModel.getData());
                break;
            case le:
                queryWrapper.le(column,selectModel.getData());
                break;
            case like:
                if(aClass == String.class){
                    queryWrapper.like(column,selectModel.getData());
                    break;
                }else {
                    throw new AdminException(AdminCode.PARAM_ERROR,selectModel.getData()+"不是String对象，不是使用like比较");
                }
            case between:
                queryWrapper.between(column,selectModel.getData(),selectModel.getDateEnd());
                break;
            default:
        }
        return queryWrapper;
    }
}
