package com.rubber.admin.framework.controller.sys;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.admin.core.base.BaseController;
import com.rubber.admin.core.entity.SysUser;
import com.rubber.admin.core.model.PagerModel;
import com.rubber.admin.core.model.ResultModel;
import com.rubber.admin.core.page.CompareModel;
import com.rubber.admin.core.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luffyu
 * Created on 2019-05-21
 */
@RestController
@RequestMapping("/sys/u")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;


    /**
     * 查询用户到列表信息
     * @param compareModel 系统到用户信息
     * @param pagerModel 用户到基本信息
     * @return
     */
    @GetMapping("/list")
    public ResultModel pageList(CompareModel compareModel, PagerModel pagerModel){
        List<CompareModel> compareModelList = new ArrayList<>();
        compareModelList.add(compareModel);
        Page<SysUser> page = creatPager(pagerModel);

        QueryWrapper<SysUser> queryWrapper = creatSearchWrapper(compareModelList,pagerModel,SysUser.class);
        sysUserService.page(page, queryWrapper);

        return ResultModel.createSuccess(page);
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
