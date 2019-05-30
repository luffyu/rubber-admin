package com.rubber.admin.core.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseEntity;
import com.rubber.admin.core.enums.MsgCode;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.util.ReflectionUtils;
import com.rubber.admin.core.util.StringTools;
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
    public static  <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(List<SelectModel> selectModels, Class<T> clz){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if(!CollectionUtils.isEmpty(selectModels)){
            Map<String,Class<?>> clzFiles = ReflectionUtils.getDBEntityFieldsName(clz);
            for(SelectModel compareModel:selectModels){
                creatSearchWrapper(queryWrapper,clzFiles,compareModel,clz);
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
    public static  <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(SelectModel selectModel, Class<T> clz){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        Map<String,Class<?>> clzFiles = ReflectionUtils.getDBEntityFieldsName(clz);
        creatSearchWrapper(queryWrapper,clzFiles,selectModel,clz);
        return queryWrapper;
    }



    /**
     * 通过单个参数查询参数信息
     * @param queryWrapper
     * @param clzFiles
     * @param selectModel
     * @param clz
     * @param <T>
     * @return
     */
    private static  <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(QueryWrapper<T> queryWrapper, Map<String,Class<?>> clzFiles, SelectModel selectModel, Class<T> clz){
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper<>();
        }
        if(StringUtils.isEmpty(selectModel.getField())){
            return queryWrapper;
        }
        if(!clzFiles.containsKey(selectModel.getField())){
            throw new AdminException(MsgCode.LOGIN_AUTH_ERROR,selectModel.getField()+"不存在"+clz.getName()+"中");
        }
        String column = StringTools.underline(selectModel.getField());
        Class<?> aClass = clzFiles.get(selectModel.getField());
        //解析比较信息
        switch (selectModel.getType()){
            case EQ:
                queryWrapper.eq(column,selectModel.getData());
                break;
            case NE:
                queryWrapper.ne(column,selectModel.getData());
                break;
            case GT:
                queryWrapper.gt(column,selectModel.getData());
                break;
            case GE:
                queryWrapper.gt(column,selectModel.getData());
                break;
            case LT:
                queryWrapper.lt(column,selectModel.getData());
                break;
            case LE:
                queryWrapper.le(column,selectModel.getData());
                break;
            case LIKE:
                if(aClass == String.class){
                    queryWrapper.like(column,selectModel.getData());
                    break;
                }else {
                    throw new AdminException(MsgCode.PARAM_ERROR,selectModel.getData()+"不是String对象，不是使用like比较");
                }
            default:
        }
        return queryWrapper;
    }
}
