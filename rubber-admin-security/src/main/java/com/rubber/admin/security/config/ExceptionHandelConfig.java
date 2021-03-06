package com.rubber.admin.security.config;

import cn.hutool.coocaa.util.exception.ExceptionUtils;
import cn.hutool.coocaa.util.result.IResultHandle;
import cn.hutool.coocaa.util.result.ResultMsg;
import cn.hutool.coocaa.util.result.exception.IResultException;
import com.rubber.admin.core.exceptions.AdminException;
import com.rubber.admin.core.exceptions.AdminRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandelConfig {


    @ExceptionHandler(value = {AdminRunTimeException.class, AdminException.class})
    @ResponseBody
    public ResultMsg handel(Exception e) throws Exception {
        ExceptionUtils.printErrorMsg(e);
        if(e instanceof IResultException){
            IResultException re = (IResultException)e;
            IResultHandle resultHandle = re.getResult();
            if( resultHandle instanceof ResultMsg){
                return (ResultMsg) resultHandle;
            }else {
                return ResultMsg.error();
            }
        }else {
            throw e;
        }
    }

}
