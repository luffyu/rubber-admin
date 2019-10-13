package com.rubber.admin.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
}
