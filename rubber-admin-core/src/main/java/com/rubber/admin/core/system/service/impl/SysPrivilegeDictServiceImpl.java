package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.plugins.security.PrivilegeAuthorizeProvider;
import com.rubber.admin.core.plugins.security.PrivilegeUtils;
import com.rubber.admin.core.system.entity.SysPrivilegeDict;
import com.rubber.admin.core.system.mapper.SysPrivilegeDictMapper;
import com.rubber.admin.core.system.model.PrivilegeBean;
import com.rubber.admin.core.system.service.ISysPrivilegeDictService;
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
public class SysPrivilegeDictServiceImpl extends BaseAdminService<SysPrivilegeDictMapper, SysPrivilegeDict> implements ISysPrivilegeDictService {



    @Override
    public List<SysPrivilegeDict> selectByType(String type) {
        if(StrUtil.isEmpty(type)){
            return null;
        }
        QueryWrapper<SysPrivilegeDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type",type);
        return list(queryWrapper);
    }


    /**
     * 获取全部的权限信息
     * @return  返回系统中所有接口所需要的权限列表
     */
    @Override
    public List<PrivilegeBean> allPrivilege() {
        //获取系统中全部Controller权限信息
        Map<String, Set<String>> allAuthorize = PrivilegeAuthorizeProvider.getAllAuthorize();
        if (CollectionUtil.isEmpty(allAuthorize)){
            return null;
        }
        List<PrivilegeBean> privilegeBeans = new ArrayList<>(allAuthorize.size());
        //获取到全部的字典信息
        List<SysPrivilegeDict> moduleDict = selectByType(PrivilegeUtils.BASIC_MODULE);

        List<SysPrivilegeDict> unitDict = selectByType(PrivilegeUtils.BASIC_UNIT);

        for(String key:allAuthorize.keySet()){
            //获取全部的key
            SysPrivilegeDict model = PrivilegeUtils.findByValue(key, moduleDict);
            PrivilegeBean privilegeBean = new PrivilegeBean(key,model);
            Set<String> unitKeys = allAuthorize.get(key);
            if(CollectionUtil.isNotEmpty(unitKeys)){
                for (String unitKey:unitKeys){
                    SysPrivilegeDict unit = PrivilegeUtils.findByValue(unitKey, unitDict);
                    String authorizeKey = PrivilegeUtils.createAuthorizeKey(key, unitKey);
                    PrivilegeBean.UnitBean unitBean = new PrivilegeBean.UnitBean(unitKey,unit,authorizeKey);
                    privilegeBean.getUnitBeans().add(unitBean);
                }
            }
            privilegeBeans.add(privilegeBean);
        }
        return privilegeBeans;
    }


}
