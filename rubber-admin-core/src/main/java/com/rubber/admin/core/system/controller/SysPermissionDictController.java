package com.rubber.admin.core.system.controller;

import cn.hutool.luffyu.util.result.ResultMsg;
import com.rubber.admin.core.base.BaseAdminController;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import com.rubber.admin.core.system.exception.DictException;
import com.rubber.admin.core.system.service.ISysPermissionDictService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>权限字典的Controller</p>
 *
 * @author luffyu
 * @date 2020-01-14 17:28
 **/
@RestController
@RequestMapping(value = "/sys/permission-dict",name = "permissionDict")
public class SysPermissionDictController extends BaseAdminController {

    @Resource
    private ISysPermissionDictService sysPermissionDictService;


    /**
     * 菜单的分页查询
     * @param json 菜单的查询信息json字符串
     * @return 返回菜单的信息
     */
    @GetMapping("/list")
    public ResultMsg list(String json){
        PageModel pageModel = decodeForJsonString(json);
        return ResultMsg.success(sysPermissionDictService.pageBySelect(pageModel, SysPermissionDict.class, null));
    }


    @PostMapping("/add")
    public ResultMsg save(@RequestBody SysPermissionDict sysPermissionDict) throws DictException {
        if(sysPermissionDict.getId() != null){
            throw new DictException(AdminCode.PARAM_ERROR,"保存的字典id必须为空");
        }
        sysPermissionDictService.verifyAndSave(sysPermissionDict);
        return ResultMsg.success();
    }


    @PostMapping("/{deptId}/update")
    public ResultMsg save(@PathVariable("deptId")Integer deptId,@RequestBody SysPermissionDict sysPermissionDict) throws  DictException {
        if(deptId == null || deptId <= 0 || !deptId.equals(sysPermissionDict.getId())){
            throw new DictException(AdminCode.PARAM_ERROR,"用户id为空");
        }
        sysPermissionDictService.verifyAndSave(sysPermissionDict);
        return ResultMsg.success();
    }
}
