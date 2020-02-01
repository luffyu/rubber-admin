package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.luffyu.util.result.code.SysCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.security.PermissionAuthorizeProvider;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import com.rubber.admin.core.system.exception.DictException;
import com.rubber.admin.core.system.mapper.SysPermissionDictMapper;
import com.rubber.admin.core.system.service.ISysPermissionDictService;
import com.rubber.admin.core.tools.ServletUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 权限字典名称 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-10-31
 */
@Service
public class SysPermissionDictServiceImpl extends BaseAdminService<SysPermissionDictMapper, SysPermissionDict> implements ISysPermissionDictService {



    @Override
    public List<SysPermissionDict> selectByType(String type) {
        if(StrUtil.isEmpty(type)){
            return null;
        }
        QueryWrapper<SysPermissionDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type",type);
        return list(queryWrapper);
    }



    @Override
    public void verifyAndSave(SysPermissionDict sysPermissionDict) throws DictException {
        if(sysPermissionDict.getId() == null){
            justSave(sysPermissionDict);
        }else {
            justUpdate(sysPermissionDict);
        }
        doAfterSave();
    }


    private void doAfterSave(){
        PermissionAuthorizeProvider.clearCache();
    }

    private void justSave(SysPermissionDict sysPermissionDict) throws DictException {
        if(findByKey(sysPermissionDict.getDictKey()) != null){
            throw new DictException(AdminCode.DICT_IS_EXIST,"key值为{}",sysPermissionDict.getDictKey());
        }
        save(sysPermissionDict);
    }

    private void justUpdate(SysPermissionDict sysPermissionDict) throws DictException {
        SysPermissionDict dbDict = findByKey(sysPermissionDict.getDictKey());
        if(dbDict == null){
            throw new DictException(AdminCode.DICT_IS_NOT_EXIST,"无法修改字典的Key");
        }
        dbDict.setDictName(sysPermissionDict.getDictName());
        dbDict.setDictType(sysPermissionDict.getDictType());
        dbDict.setRemark(sysPermissionDict.getRemark());
        dbDict.setStatus(sysPermissionDict.getStatus());

        Integer loginUserId = ServletUtils.getLoginUserId();
        dbDict.setUpdateBy(loginUserId);
        dbDict.setUpdateTime(new Date());
        if(!updateById(dbDict)){
            throw new DictException(SysCode.SYSTEM_ERROR,"更新字典信息异常");
        }
    }


    /**
     * 通过key中获取当前的数据信息
     * @param key
     * @return
     */
    private SysPermissionDict findByKey(String key){
        QueryWrapper<SysPermissionDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_key",key);
        return getOne(queryWrapper);
    }
}
