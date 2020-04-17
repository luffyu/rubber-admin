package com.rubber.admin.core.authorize.service;

import com.rubber.admin.core.authorize.entity.AuthGroupConfig;
import com.rubber.admin.core.authorize.exception.AuthGroupException;
import com.rubber.admin.core.authorize.model.RubberGroupTypeEnums;
import com.rubber.admin.core.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限族群配置表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2020-03-12
 */
public interface IAuthGroupConfigService extends IBaseService<AuthGroupConfig> {


    /**
     * 通过类型返回 对应的组key值 和组成员头
     * @param rubberGroupEnums 类型
     * @return 返回组key值 和组成员头 map关系图
     */
    List<AuthGroupConfig> findGroupAndMemberByType(RubberGroupTypeEnums rubberGroupEnums);


    /**
     * 通过成员匹配元素信息
     * @param key 关键字key
     * @return 返回符合要求的族群信息
     */
    AuthGroupConfig startWithByMember(List<AuthGroupConfig> authGroupConfigs,String key);


    /**
     * 获取操心类型的映射名称
     * @return 返回当前的值
     */
    Map<String,String> getOptionMap();


    /**
     * 验证并保存参数信息
     * @param authGroupConfig 权限族群信息
     * @throws AuthGroupException 异常信息
     */
    void verifyAndSave(AuthGroupConfig authGroupConfig) throws AuthGroupException;


    /**
     * 通过id删除
     * @param id 配置id
     * @throws AuthGroupException 异常信息
     */
    void verifyAndRemove(Integer id) throws AuthGroupException;
}
