package com.rubber.admin.core.base;

import cn.hutool.coocaa.util.result.code.SysCode;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.rubber.admin.core.exceptions.AdminRunTimeException;
import com.rubber.admin.core.plugins.page.PageModel;

/**
 * <p>基础的Controller方法</p>
 *
 * @author luffyu
 * @date 2020-01-14 14:49
 **/
public class BaseAdminController {

    /**
     * 把查询的json结构转化成对象结构
     * @param json json字符串
     * @return 返回对象信息
     */
    protected PageModel decodeForJsonString(String json){
        if(StrUtil.isEmpty(json)){
            return new PageModel();
        }
        try{
            String decode = URLUtil.decode(json);
            return JSON.parseObject(decode,PageModel.class);
        }catch (Exception e){
            throw new AdminRunTimeException(SysCode.PARAM_ERROR,"请求的参数必须是json结构");
        }
    }


}
