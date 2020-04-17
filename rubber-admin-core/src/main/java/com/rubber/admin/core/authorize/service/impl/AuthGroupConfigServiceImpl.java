package com.rubber.admin.core.authorize.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rubber.admin.core.authorize.RubberAuthorizeGroupContext;
import com.rubber.admin.core.authorize.entity.AuthGroupConfig;
import com.rubber.admin.core.authorize.exception.AuthGroupException;
import com.rubber.admin.core.authorize.mapper.AuthGroupConfigMapper;
import com.rubber.admin.core.authorize.model.RubberGroupTypeEnums;
import com.rubber.admin.core.authorize.service.IAuthGroupConfigService;
import com.rubber.admin.core.base.BaseAdminService;
import com.rubber.admin.core.enums.AdminCode;
import com.rubber.admin.core.plugins.cache.ICacheProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private ICacheProvider iCacheProvider;

    @Resource
    private RubberAuthorizeGroupContext rubberAuthorizeGroupContext;


    @Override
    public List<AuthGroupConfig> findGroupAndMemberByType(RubberGroupTypeEnums rubberGroupEnums) {
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
        List<AuthGroupConfig> authGroupConfigs = queryByType(RubberGroupTypeEnums.option.toString());
        if(CollUtil.isEmpty(authGroupConfigs)){
            return new HashMap<>(2);
        }
        return authGroupConfigs.stream().collect(Collectors.toMap(AuthGroupConfig::getGroupKey,AuthGroupConfig::getGroupName));
    }




    @Override
    public void verifyAndSave(AuthGroupConfig authGroupConfig) throws AuthGroupException {
        if (authGroupConfig.getGroupId() == null){
            doSave(authGroupConfig);
        }else {
            doUpdate(authGroupConfig);
        }
        doAfterSaveGroupConfig();

    }

    @Override
    public void verifyAndRemove(Integer id) throws AuthGroupException {
        AuthGroupConfig config = getById(id);
        if (config == null){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"不存在id为{}的数据",id);
        }
        removeById(id);
    }


    /**
     * 更新族群配置信息之后的操作
     */
    private void doAfterSaveGroupConfig(){
        //处置版本信息
        rubberAuthorizeGroupContext.initGroupDict(true);
        //更新版本
        iCacheProvider.incrVersion();
    }


    private void doSave(AuthGroupConfig authGroupConfig) throws AuthGroupException {
        if (StrUtil.isEmpty(authGroupConfig.getGroupKey())){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"key值为空");
        }
        AuthGroupConfig byGroupKey = getByGroupKey(authGroupConfig.getGroupKey());
        if (byGroupKey != null){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"key {}值已经存在",authGroupConfig.getGroupKey());
        }
        if (!save(authGroupConfig)){
            throw new AuthGroupException(AdminCode.AUTH_CONFIG_WRITE_ERROR,"组配置信息保存到数据库失败");
        }
    }

    private void doUpdate(AuthGroupConfig authGroupConfig) throws AuthGroupException {
        if (StrUtil.isEmpty(authGroupConfig.getGroupKey())){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"Key值为空");
        }
        AuthGroupConfig dbGroupConfig = getByGroupKey(authGroupConfig.getGroupKey());
        if (dbGroupConfig == null){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"不存在key值为{}到数据",authGroupConfig.getGroupKey());
        }
        if (!authGroupConfig.getGroupKey().equals(dbGroupConfig.getGroupKey())){
            throw new AuthGroupException(AdminCode.PARAM_ERROR,"Key值{}不能修改",authGroupConfig.getGroupKey());
        }
        if (!updateById(authGroupConfig)){
            throw new AuthGroupException(AdminCode.AUTH_CONFIG_WRITE_ERROR,"组配置信息更新到数据库失败");
        }
    }






    /**
     * 通过类型查询
     * @param type 类型
     * @return 返回对应的list集合
     */
    private List<AuthGroupConfig> queryByType(String type){
        QueryWrapper<AuthGroupConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_type",type);
        return list(queryWrapper);
    }


    /**
     * 通过key值读取配置信息
     * @param key 当前的key值
     * @return 返回key值信息
     */
    private AuthGroupConfig getByGroupKey(String key){
        QueryWrapper<AuthGroupConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_key",key);
        return getOne(queryWrapper);
    }
}
