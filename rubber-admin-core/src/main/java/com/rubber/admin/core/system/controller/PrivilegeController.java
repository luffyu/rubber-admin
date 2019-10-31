package com.rubber.admin.core.system.controller;

import cn.hutool.coocaa.util.result.ResultMsg;
import com.rubber.admin.core.system.model.PrivilegeBean;
import com.rubber.admin.core.system.service.ISysPrivilegeDictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author luffyu
 * Created on 2019-10-31
 */
@Controller
@RequestMapping(value = "/sys-privilege",name = "sys-privilege")
public class PrivilegeController {


    @Resource
    private ISysPrivilegeDictService sysPrivilegeDictService;


    /**
     * 获取系统中所有的权限列表
     * @param request 请求参数
     * @return 返回权限列表
     */
    @GetMapping("/list")
    public ResultMsg listAll(HttpServletRequest request){
        List<PrivilegeBean> privilegeBeans = sysPrivilegeDictService.allPrivilege();
        return ResultMsg.success(privilegeBeans);
    }
}
