package com.rubber.admin.core.authorize.controller;

import cn.hutool.luffyu.util.result.ResultMsg;
import com.rubber.admin.core.authorize.RubberAuthorizeGroupContext;
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
    private RubberAuthorizeGroupContext rubberAuthorizeGroupContext;


    @GetMapping("/apply-tree")
    public ResultMsg getApplyTree(){
        return ResultMsg.success(rubberAuthorizeGroupContext.getAllApplyTree());
    }



    @GetMapping("/option-tree")
    public ResultMsg getOptionTree(){
        return ResultMsg.success(rubberAuthorizeGroupContext.getAllOptionTree());
    }
}
