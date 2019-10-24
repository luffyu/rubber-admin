package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@RestController
@RequestMapping(value = "/menu",name = "menu")
public class MenuController {

    @RequestMapping("/list")
    public ResultMsg list(){
        return ResultMsg.success();
    }
}
