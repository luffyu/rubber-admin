package com.rubber.admin.core.system.controller;


import cn.hutool.coocaa.util.result.ResultMsg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.page.PageModel;
import com.rubber.admin.core.system.entity.SysDept;
import com.rubber.admin.core.system.exception.DeptException;
import com.rubber.admin.core.system.service.ISysDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-11-06
 */
@RestController
@RequestMapping(value = "/sys/dept",name = "dept")
public class SysDeptController {

    @Resource
    private ISysDeptService sysDeptService;




    /**
     * 部门的分页查询
     * @param pageModel 部门的查询信息
     * @return 返回部门的信息
     */
    @GetMapping("/list")
    public ResultMsg list(@RequestBody PageModel pageModel){
        IPage<SysDept> sysRoleIPage = sysDeptService.pageBySelect(pageModel, SysDept.class, null);
        return ResultMsg.success(sysRoleIPage);
    }




    /**
     * 添加部门
     * @return 返回添加部门的基本信息
     */
    @PostMapping("/add")
    public ResultMsg addDept(@RequestBody SysDept sysDept) throws DeptException {
        if(sysDept.getDeptId() != null){
            throw new DeptException(AdminCode.PARAM_ERROR,"保存的部门id必须为空");
        }
        sysDeptService.saveOrUpdateDept(sysDept);
        return ResultMsg.success();
    }



    /**
     * 更新部门
     * @return 返回部门更新的值
     */
    @PostMapping("/{deptId}/update")
    public ResultMsg updateDept(@PathVariable("deptId")Integer deptId,@RequestBody SysDept sysDept) throws DeptException {
        if(deptId == null || deptId <= 0 ){
            throw new DeptException(AdminCode.PARAM_ERROR,"部门id不存在");
        }
        if(!deptId.equals(sysDept.getDeptId())){
            throw new DeptException(AdminCode.PARAM_ERROR,"部门id不存在");
        }
        sysDeptService.saveOrUpdateDept(sysDept);
        return ResultMsg.success();
    }


    /**
     * 更新部门
     * @return 返回部门更新的值
     */
    @PostMapping("/{deptId}/del")
    public ResultMsg delDept(@PathVariable("deptId")Integer deptId) throws DeptException {
        if(deptId == null || deptId <= 0 ){
            throw new DeptException(AdminCode.PARAM_ERROR,"部门id不存在");
        }
        sysDeptService.delById(deptId);
        return ResultMsg.success();
    }



    /**
     * 获取部门详细信息
     * @return 返回部门更新的值
     */
    @GetMapping("/{deptId}/info")
    public ResultMsg getInfo(@PathVariable("deptId")Integer deptId) throws DeptException {
        if(deptId == null || deptId <= 0 ){
            throw new DeptException(AdminCode.PARAM_ERROR,"部门id不存在");
        }
        SysDept sysDept = sysDeptService.getAndVerifyById(deptId);
        return ResultMsg.success(sysDept);
    }


}
