package com.rubber.admin.core.authorize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rubber.admin.core.authorize.model.RubberGroupEnums;
import com.rubber.admin.core.authorize.entity.AuthGroupConfig;

import java.util.List;

/**
 * <p>
 * 权限族群配置表 服务类
 * </p>
 *
 * @author luffyu-auto
 * @since 2020-03-12
 */
public interface IAuthGroupConfigService extends IService<AuthGroupConfig> {


    /**
     * 通过类型返回 对应的组key值 和组成员头
     * @param rubberGroupEnums 类型
     * @return 返回组key值 和组成员头 map关系图
     */
    List<AuthGroupConfig> findGroupAndMemberByType(RubberGroupEnums rubberGroupEnums);


    /**
     * 通过成员匹配元素信息
     * @param key
     * @return
     */
    AuthGroupConfig startWithByMember(List<AuthGroupConfig> authGroupConfigs,String key);

}
