package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.system.entity.SysMenu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@RestController
@RequestMapping(value = "/sys/menu",name = "menu")
public class MenuController  {

    @GetMapping("/list")
    public ResultMsg list(){
        return ResultMsg.success();
    }


    /**
     * 添加菜单
     * @return
     */
    @PostMapping("/add")
    public ResultMsg addMenu(SysMenu sysMenu){
        return ResultMsg.success();
    }
}
