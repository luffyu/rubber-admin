package com.rubber.admin.framework.controller.sys;

import com.rubber.admin.core.model.ResultModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luffyu
 * Created on 2019-05-21
 */
@RestController
@RequestMapping("/sys/u")
public class SysUserController {


    @GetMapping("/list")
    public ResultModel pageList(){
        return ResultModel.createSuccess();
    }


    @PostMapping("/save")
    public ResultModel save(){
        return ResultModel.createSuccess();
    }



    @PostMapping("/del")
    public ResultModel del(){
        return ResultModel.createSuccess();
    }

}
