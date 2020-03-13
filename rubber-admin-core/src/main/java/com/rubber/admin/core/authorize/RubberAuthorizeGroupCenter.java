package com.rubber.admin.core.authorize;

import cn.hutool.core.collection.CollUtil;
import com.rubber.admin.core.authorize.entity.AuthGroupConfig;
import com.rubber.admin.core.authorize.model.GroupControllerModel;
import com.rubber.admin.core.authorize.model.GroupMappingModel;
import com.rubber.admin.core.authorize.model.RequestOriginBean;
import com.rubber.admin.core.authorize.model.RubberGroupEnums;
import com.rubber.admin.core.authorize.service.IAuthGroupConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author luffyu
 * @date 2020-03-12 20:15
 **/
@Slf4j
@Component
public class RubberAuthorizeGroupCenter {

    /**
     * 当前url的对接认证
     */
    private Map<String,String> urlAuthDict = new ConcurrentHashMap<>(100);

    /**
     * 当前组映射对象集合
     */
    public List<GroupControllerModel> groupDict = new ArrayList<>();


    /**
     * 对象锁
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * 族群配置中心
     */
    @Resource
    IAuthGroupConfigService authGroupConfigService;


    /**
     * 返回一个url 所需要的权限
     * @param url 当前的url信息
     * @return 返回url所需要的权限
     */
    public String getByUrl(String url){
        return this.urlAuthDict.get(url);
    }



    /**
     * 读取url的权限认证字典
     * @return
     */
    public Map<String,String> getUrlAuthDict(){
        lock.readLock().lock();
        try {
            if (CollUtil.isEmpty(this.urlAuthDict)){
                initGroupDict(true);
            }
            return this.urlAuthDict;
        }finally {
            lock.readLock().unlock();
        }
    }


    /**
     * 获取全部的族群信息
     * @return 返回当前的数据信息
     */
    public List<GroupControllerModel> getAllGroupDict(){
        lock.readLock().lock();
        try {
            if (CollUtil.isEmpty(this.groupDict)){
                initGroupDict(true);
            }
            return this.groupDict;
        }finally {
            lock.readLock().unlock();
        }
    }


    /**
     * 清除缓存
     */
    public void clearCache(){
        groupDict.clear();
        urlAuthDict.clear();

    }



    /**
     * 初始化族群信息
     */
    public void initGroupDict(boolean reset){
        lock.writeLock().lock();
        try {
            log.info("开始初始化权限组数据信息......");
            if(reset){
                clearCache();
            }
            List<RequestOriginBean> requestOriginBeans = initRequestOriginGroup();
            initGroupMappingModel(requestOriginBeans);
            log.info("初始化权限组数据信息成功......");
        }finally {
            lock.writeLock().unlock();
        }
    }


    /**
     * 1：初始化族群信息
     * 2: 写入url 对应的权限信息
     *
     * @return 返回加入族群之后的 RequestOriginBean 集合
     */
    private List<RequestOriginBean> initRequestOriginGroup(){
        List<RequestOriginBean> requestOriginList = RequestOriginProvider.getRequestOriginBeans();
        if(CollUtil.isNotEmpty(requestOriginList)){
            List<AuthGroupConfig> groupController = authGroupConfigService.findGroupAndMemberByType(RubberGroupEnums.controller);
            List<AuthGroupConfig> groupMapping = authGroupConfigService.findGroupAndMemberByType(RubberGroupEnums.mapping);
            for (RequestOriginBean requestOriginBean:requestOriginList){
                handleGroupAuthKey(requestOriginBean,groupController,groupMapping);
                String authKey = AuthorizeTools.createAuthKey(requestOriginBean.getEffectiveControllerKey(),requestOriginBean.getEffectiveMappingKey());
                urlAuthDict.put(requestOriginBean.getUrl(),authKey);
            }
        }
        return requestOriginList;
    }


    /**
     * 1: 返回全部的族群权限新
     *
     * @param requestOriginList 对应的请求原始信息数据
     */
    private void initGroupMappingModel(List<RequestOriginBean> requestOriginList){
        if(CollUtil.isEmpty(requestOriginList)){
            return;
        }
        Map<String, List<RequestOriginBean>> collect = requestOriginList.stream().collect(Collectors.groupingBy(RequestOriginBean::getEffectiveControllerKey));
        for (String key:collect.keySet()){
            List<RequestOriginBean> controllerRequest = collect.get(key);
            if(CollUtil.isEmpty(controllerRequest)){
                continue;
            }
            GroupControllerModel groupControllerModel = new GroupControllerModel(controllerRequest.get(0));

            Map<String, List<RequestOriginBean>> mappingRequest = controllerRequest.stream().collect(Collectors.groupingBy(RequestOriginBean::getEffectiveMappingKey));
            List<GroupMappingModel> groupMappingModels = new ArrayList<>();
            for(String mappingKey:mappingRequest.keySet()){
                List<RequestOriginBean> requestOriginBeans = mappingRequest.get(mappingKey);
                GroupMappingModel mappingModel = new GroupMappingModel(requestOriginBeans.get(0));
                Set<String> urls = requestOriginBeans.stream().map(RequestOriginBean::getUrl).collect(Collectors.toSet());
                mappingModel.setUrls(urls);
                groupMappingModels.add(mappingModel);
            }
            groupControllerModel.setMappingModels(groupMappingModels);
            groupDict.add(groupControllerModel);
        }
    }


    /**
     * 映射族群
     * @param requestOriginBean
     * @param groupController
     * @param groupMapping
     */
    private void handleGroupAuthKey(RequestOriginBean requestOriginBean, List<AuthGroupConfig> groupController, List<AuthGroupConfig> groupMapping){

        AuthGroupConfig cGroup = authGroupConfigService.startWithByMember(groupController, requestOriginBean.getControllerKey());
        if (cGroup != null){
            requestOriginBean.setControllerGroup(cGroup);
        }else {
            requestOriginBean.setControllerGroup(null);
        }
        AuthGroupConfig mGroup = authGroupConfigService.startWithByMember(groupMapping, requestOriginBean.getMappingKey());
        if (mGroup != null){
            requestOriginBean.setMappingGroup(mGroup);
        }else {
            requestOriginBean.setMappingGroup(null);
        }
    }


}
