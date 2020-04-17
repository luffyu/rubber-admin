package com.rubber.admin.core.authorize.controller;

import cn.hutool.luffyu.util.result.ResultMsg;
import com.rubber.admin.core.authorize.entity.AuthGroupConfig;
import com.rubber.admin.core.authorize.exception.AuthGroupException;
import com.rubber.admin.core.authorize.model.RubberGroupTypeEnums;
import com.rubber.admin.core.authorize.service.IAuthGroupConfigService;
import com.rubber.admin.core.base.BaseAdminController;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.page.PageModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-18 15:54
 **/
@RestController
@RequestMapping("/auth/config")
public class AuthGroupConfigController extends BaseAdminController {


    @Resource
    private IAuthGroupConfigService iAuthGroupConfigService;


    /**
     * 获取页面的基本配置参数
     * @return 活动id
     */
    @GetMapping("/view-info")
    public ResultMsg getViewInfo(){
        Map<String,Object> groupConfigInfo = new HashMap<>();
        groupConfigInfo.put("type", RubberGroupTypeEnums.getGroupTypeInfo());
        return ResultMsg.success(groupConfigInfo);
    }


    /**
     * 部门的分页查询
     * @param json 部门的查询信息的json字符串
     * @return 返回部门的信息
     */
    @GetMapping("/list")
    public ResultMsg list(String json){
        PageModel pageModel = decodeForJsonString(json);
        return ResultMsg.success(iAuthGroupConfigService.pageBySelect(pageModel, AuthGroupConfig.class, null));
    }



    @PostMapping("/add")
    public ResultMsg save(@RequestBody AuthGroupConfig authGroupConfig) throws AuthGroupException {
        if(authGroupConfig.getGroupId() != null){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"保存的字典id必须为空");
        }
        iAuthGroupConfigService.verifyAndSave(authGroupConfig);
        return ResultMsg.success();
    }


    @PostMapping("/update/{groupId}")
    public ResultMsg update(@PathVariable("groupId")Integer groupId,@RequestBody AuthGroupConfig authGroupConfig) throws  AuthGroupException {
        if(groupId == null || groupId <= 0 || !groupId.equals(authGroupConfig.getGroupId())){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"用户id为空");
        }
        iAuthGroupConfigService.verifyAndSave(authGroupConfig);
        return ResultMsg.success();
    }


    @PostMapping("/delete/{groupId}")
    public ResultMsg delete(@PathVariable("groupId")Integer groupId) throws  AuthGroupException {
        if(groupId == null || groupId <= 0){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"用户id为空");
        }
        iAuthGroupConfigService.verifyAndRemove(groupId);
        return ResultMsg.success();
    }

}
