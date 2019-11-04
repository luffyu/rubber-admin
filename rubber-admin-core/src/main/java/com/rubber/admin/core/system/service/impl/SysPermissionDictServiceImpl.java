package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.plugins.security.PermissionAuthorizeProvider;
import com.rubber.admin.core.plugins.security.PermissionUtils;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import com.rubber.admin.core.system.mapper.SysPermissionDictMapper;
import com.rubber.admin.core.system.model.PermissionBean;
import com.rubber.admin.core.system.service.ISysPermissionDictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


    /**
     * 获取全部的权限信息
     * @return  返回系统中所有接口所需要的权限列表
     */
    @Override
    public List<PermissionBean> allPermission() {
        //获取系统中全部Controller权限信息
        Map<String, Set<String>> allAuthorize = PermissionAuthorizeProvider.getAllAuthorize();
        if (CollectionUtil.isEmpty(allAuthorize)){
            return null;
        }
        List<PermissionBean> permissionBeans = new ArrayList<>(allAuthorize.size());
        //获取到全部的字典信息
        List<SysPermissionDict> moduleDict = selectByType(PermissionUtils.BASIC_MODULE);

        List<SysPermissionDict> unitDict = selectByType(PermissionUtils.BASIC_UNIT);

        for(String key:allAuthorize.keySet()){
            //获取全部的key
            SysPermissionDict model = PermissionUtils.findByValue(key, moduleDict);
            PermissionBean permissionBean = new PermissionBean(key,model);
            Set<String> unitKeys = allAuthorize.get(key);
            if(CollectionUtil.isNotEmpty(unitKeys)){
                for (String unitKey:unitKeys){
                    SysPermissionDict unit = PermissionUtils.findByValue(unitKey, unitDict);
                    String authorizeKey = PermissionUtils.createAuthorizeKey(key, unitKey);
                    PermissionBean.UnitBean unitBean = new PermissionBean.UnitBean(unitKey,unit,authorizeKey);
                    permissionBean.getUnitBeans().add(unitBean);
                }
            }
            permissionBeans.add(permissionBean);
        }
        return permissionBeans;
    }


}
