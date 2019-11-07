package com.rubber.admin.core.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import com.rubber.admin.core.system.mapper.SysPermissionDictMapper;
import com.rubber.admin.core.system.service.ISysPermissionDictService;
import org.springframework.stereotype.Service;

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



}
