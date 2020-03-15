package com.rubber.admin.core.authorize.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.authorize.model.RubberGroupEnums;
import com.rubber.admin.core.authorize.entity.AuthGroupConfig;
import com.rubber.admin.core.authorize.mapper.AuthGroupConfigMapper;
import com.rubber.admin.core.authorize.service.IAuthGroupConfigService;
import com.rubber.admin.core.base.BaseAdminService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限族群配置表 服务实现类
 * </p>
 *
 * @author luffyu-auto
 * @since 2020-03-12
 */
@Service
public class AuthGroupConfigServiceImpl extends BaseAdminService<AuthGroupConfigMapper, AuthGroupConfig> implements IAuthGroupConfigService {



    @Override
    public List<AuthGroupConfig> findGroupAndMemberByType(RubberGroupEnums rubberGroupEnums) {
        if (rubberGroupEnums == null){
            return null;
        }
        return queryByType(rubberGroupEnums.toString());
    }



    @Override
    public AuthGroupConfig startWithByMember(List<AuthGroupConfig> authGroupConfigs, String key) {
        if(CollectionUtil.isEmpty(authGroupConfigs)){
            return null;
        }
        for(AuthGroupConfig sysPrivilegeDict:authGroupConfigs){
            if(StrUtil.startWithAny(key,sysPrivilegeDict.getMembers())){
                return sysPrivilegeDict;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getOptionMap() {
        List<AuthGroupConfig> authGroupConfigs = queryByType(RubberGroupEnums.option.toString());
        if(CollUtil.isEmpty(authGroupConfigs)){
            return new HashMap<>();
        }
        return authGroupConfigs.stream().collect(Collectors.toMap(AuthGroupConfig::getGroupKey,AuthGroupConfig::getGroupName));
    }


    public List<AuthGroupConfig> queryByType(String type){
        QueryWrapper<AuthGroupConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_type",type);
        return list(queryWrapper);
    }
}
