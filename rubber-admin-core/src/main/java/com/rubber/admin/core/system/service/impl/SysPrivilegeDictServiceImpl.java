package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseService;
import com.rubber.admin.core.system.entity.SysPrivilegeDict;
import com.rubber.admin.core.system.mapper.SysPrivilegeDictMapper;
import com.rubber.admin.core.system.service.ISysPrivilegeDictService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限字典名称 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2019-10-31
 */
@Service
public class SysPrivilegeDictServiceImpl extends BaseService<SysPrivilegeDictMapper, SysPrivilegeDict> implements ISysPrivilegeDictService {



    @Override
    public List<SysPrivilegeDict> selectByType(String type) {
        if(StrUtil.isEmpty(type)){
            return null;
        }
        QueryWrapper<SysPrivilegeDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type",type);
        return list(queryWrapper);
    }



    @Override
    public Map<SysPrivilegeDict, Set<String>> selectDictByType(String type) {
        List<SysPrivilegeDict> sysPrivilegeDicts = selectByType(type);
        if(CollectionUtil.isEmpty(sysPrivilegeDicts)){
            return null;
        }
        Map<SysPrivilegeDict, Set<String>> map = new HashMap<>();


        sysPrivilegeDicts.stream().collect(Collectors.toMap(SysPrivilegeDict::getDictKey,SysPrivilegeDict::getDictValue));
        for(SysPrivilegeDict sysPrivilegeDict:sysPrivilegeDicts){
            String dictValue = sysPrivilegeDict.getDictValue();

        }

        return null;
    }


}
