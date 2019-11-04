package com.rubber.admin.test.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.plugins.security.PermissionAuthorizeProvider;
import com.rubber.admin.core.system.model.PermissionBean;
import com.rubber.admin.core.system.service.impl.SysPermissionDictServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        List<PermissionBean> privilegeBeans = sysPrivilegeDictService.allPermission();
        return ResultMsg.success(privilegeBeans);
    }

    @RequestMapping("/sys-time-2")
    public ResultMsg getSysTime2(){

        Map<String, String> mappingAuthorize = PermissionAuthorizeProvider.getMappingAuthorize();

        return ResultMsg.success(mappingAuthorize);
    }
}
