package com.rubber.admin.framework.config;

import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author luffyu
 * Created on 2019-05-27
 *
 * 异常信息的拦截 只拦截AdminException 为后台中的异常信息
 */
@ControllerAdvice
public class ExceptionHandel {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandel.class);


    @ExceptionHandler(value = AdminException.class)
    @ResponseBody
    public ResultModel handel(AdminException e) throws Exception {
        e.printStackTrace();
        StackTraceElement se = e.getStackTrace()[0];
        logger.error("错误code:{},错误msg:{} 错误data:{},错误来源{},方法{},行数{}",
                e.getCode(),e.getMsg(),e.getData(),se.getClassName(),se.getMethodName(),se.getLineNumber());
        return ResultModel.createError(e.getCode(),e.getMsg(),e.getData());
    }

}
