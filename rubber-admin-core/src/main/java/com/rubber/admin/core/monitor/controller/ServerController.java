package com.rubber.admin.core.monitor.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.monitor.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luffyu
 * Created on 2019-11-05
 * 系统服务器的监控信息
 */
@RestController
@RequestMapping(value = "/monitor/server",name = "monitor-server")
public class ServerController {


    @GetMapping("/info")
    public ResultMsg getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return ResultMsg.success(server);
    }
}
