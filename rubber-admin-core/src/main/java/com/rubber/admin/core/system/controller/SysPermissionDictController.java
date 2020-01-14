package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.base.BaseAdminController;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import com.rubber.admin.core.system.service.ISysPermissionDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
