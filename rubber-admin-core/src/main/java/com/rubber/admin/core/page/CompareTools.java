package com.rubber.admin.core.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseEntity;
import com.rubber.admin.core.enums.MsgCode;
import com.rubber.admin.core.exceptions.base.BaseException;
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
public class CompareTools {


    /**
     * 通过传入的参数信息来比较对象信息
     * @param compareModels 参数信息
     * @param clz 对象信息
     * @param <T> entit的类名称
     * @return 返回查询的参数
     */
    public static  <T extends BaseEntity> QueryWrapper<T> creatSearchWrapper(List<CompareModel> compareModels,Class<T> clz){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if(!CollectionUtils.isEmpty(compareModels)){
            Map<String,Class<?>> clzFiles = ReflectionUtils.getDBEntityFieldsName(clz);

            for(CompareModel compareModel:compareModels){
                if(StringUtils.isEmpty(compareModel.getField())){
                    continue;
                }
                if(!clzFiles.containsKey(compareModel.getField())){
                    throw new BaseException(MsgCode.LOGIN_AUTH_ERROR,compareModel.getField()+"不存在"+clz.getName()+"中");
                }
                String column = StringTools.underline(compareModel.getField());
                Class<?> aClass = clzFiles.get(compareModel.getField());
                //解析比较信息
                switch (compareModel.getType()){
                    case EQ:
                        queryWrapper.eq(column,compareModel.getData());
                        break;
                    case NE:
                        queryWrapper.ne(column,compareModel.getData());
                        break;
                    case GT:
                        queryWrapper.gt(column,compareModel.getData());
                        break;
                    case GE:
                        queryWrapper.gt(column,compareModel.getData());
                        break;
                    case LT:
                        queryWrapper.lt(column,compareModel.getData());
                        break;
                    case LE:
                        queryWrapper.le(column,compareModel.getData());
                        break;
                    case LIKE:
                        if(aClass == String.class){
                            queryWrapper.like(column,compareModel.getData());
                            break;
                        }else {
                            throw new BaseException(MsgCode.PARAM_ERROR,compareModel.getData()+"不是String对象，不是使用like比较");
                        }
                   default:
                }
            }
        }
        return queryWrapper;
    }
}
