package com.rubber.admin.security.config;

import com.luffyu.piece.utils.result.IResultHandle;
import com.luffyu.piece.utils.result.ResultMsg;
import com.rubber.admin.core.exceptions.AdminException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandelConfig {


    @ExceptionHandler(value = AdminException.class)
    @ResponseBody
    public ResultMsg handel(AdminException e) {
        IResultHandle resultHandle = e.getResult();
        if( resultHandle instanceof ResultMsg){
            return (ResultMsg) resultHandle;
        }else {
            return ResultMsg.error();
        }
    }

}
