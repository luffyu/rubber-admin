package com.rubber.admin.test.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.plugins.security.HandlerMappingAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author luffyu
 * Created on 2019-10-13
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping("/sys-time")
    public String getSysTime(){

        return String.valueOf(new Date());
    }


    @RequestMapping("/sys-time-1")
    public ResultMsg getSysTime1(){
        Map<String, Set<String>> allAuthorize = HandlerMappingAuthorize.getAllAuthorize();
        return ResultMsg.success(allAuthorize);
    }

    @RequestMapping("/sys-time-2")
    public ResultMsg getSysTime2(){

        Map<String, String> mappingAuthorize = HandlerMappingAuthorize.getMappingAuthorize();

        return ResultMsg.success(mappingAuthorize);
    }
}
