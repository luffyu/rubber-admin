package com.rubber.admin.test.controller;

import cn.hutool.luffyu.util.result.ResultMsg;
import com.rubber.admin.core.system.model.SysUserRoleModel;
import com.rubber.admin.core.system.service.impl.SysPermissionDictServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    SysPermissionDictServiceImpl sysPrivilegeDictService;

    @RequestMapping("/sys-time")
    public String getSysTime(){

        return String.valueOf(new Date());
    }


    @RequestMapping("/sys-time-1")
    public ResultMsg getSysTime1(){
        return ResultMsg.success(null);
    }

    @RequestMapping("/sys-time-2")
    public ResultMsg getSysTime2(SysUserRoleModel roleModel){


        return ResultMsg.success(null);
    }
}
