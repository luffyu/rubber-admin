package com.rubber.admin.framework.controller;

import com.rubber.admin.core.enums.MsgCode;
import com.rubber.admin.core.model.ResultModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-05-15
 */
@RestController
public class CommonController {

    /**
     * 没有操作权限到默认操作地址
     * @return 返回没有权限的地址信息
     */
    @RequestMapping("/auth-error")
    public ResultModel resultAuthError(){
        return ResultModel.createError(MsgCode.SHIRO_AUTH_ERROR);
    }


    /**
     * 获取系统时间
     * @param request
     * @return
     */
    @GetMapping("/sys-time")
    public ResultModel resultSysTime(HttpServletRequest request){
        return ResultModel.createSuccess(System.currentTimeMillis());
    }


}
