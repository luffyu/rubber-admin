package com.rubber.admin.test.controller;

import com.rubber.admin.test.service.MyMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luffyu
 * Created on 2019-05-29
 */
@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    private MyMsgService myMsgService;

    @RequestMapping("/send")
    public String getSens(String msg){
        myMsgService.send(msg);
        return "true";
    }


    @RequestMapping("/lister")
    public String lister(){
        myMsgService.liseter();
        return "true";
    }
}
