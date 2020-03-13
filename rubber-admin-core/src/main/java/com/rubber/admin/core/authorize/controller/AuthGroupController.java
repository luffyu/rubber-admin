package com.rubber.admin.core.authorize.controller;

import cn.hutool.luffyu.util.result.ResultMsg;
import com.rubber.admin.core.authorize.RubberAuthorizeGroupCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-12 18:08
 **/
@RestController
@RequestMapping("/auth/group")
public class AuthGroupController {


    @Autowired
    private RubberAuthorizeGroupCenter rubberAuthorizeGroupCenter;


    @GetMapping("/info")
    public ResultMsg list(){
        return ResultMsg.success(rubberAuthorizeGroupCenter.getAllGroupDict());

    }
}
