package com.rubber.admin.controller;

import com.rubber.admin.framework.shiro.session.RedisSessionTools;
import com.rubber.admin.service.Subscriber;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
@RestController
public class MyTest {




    @RequestMapping("/shiro/anon1")
    public String getName(){

        return "test-anon1";
    }

    @RequestMapping("/shiro/anon2")
    public String getName2(){

        return "test-anon2";
    }

    @RequestMapping("/shiro/anon3")
    public String getName3(){
        return "test-anon3";
    }


    @RequestMapping("/shiro/role-test")
    @RequiresRoles("user")
    public String role(){
        return "role true";
    }



    @RequestMapping("/shiro/add")
    @RequiresPermissions("sys:v3:add")
    public Object add(){
        String msg = "asfsafda";
        Object data = SecurityUtils.getSubject().getPrincipal();

        Session session = SecurityUtils.getSubject().getSession();
        return data;
    }








}
