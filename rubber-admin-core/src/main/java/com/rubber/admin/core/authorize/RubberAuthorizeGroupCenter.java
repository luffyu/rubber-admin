package com.rubber.admin.core.authorize;

import cn.hutool.core.collection.CollUtil;
import com.rubber.admin.core.authorize.entity.AuthGroupConfig;
import com.rubber.admin.core.authorize.model.GroupOptionApplyTreeModel;
import com.rubber.admin.core.authorize.model.RequestOriginBean;
import com.rubber.admin.core.authorize.model.RubberGroupTypeEnums;
import com.rubber.admin.core.authorize.service.IAuthGroupConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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



    private List<GroupOptionApplyTreeModel> allOptionTree = new ArrayList<>();



    private List<GroupOptionApplyTreeModel> allApplyTree = new ArrayList<>();

    /**
     * 对象锁
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * 族群配置中心
     */
    @Resource
    IAuthGroupConfigService authGroupConfigService;



    public Map<String, String> getUrlAuthDict() {
        return urlAuthDict;
    }

    public List<GroupOptionApplyTreeModel> getAllOptionTree() {
        return allOptionTree;
    }

    public List<GroupOptionApplyTreeModel> getAllApplyTree() {
        return allApplyTree;
    }

    /**
     * 初始化族群信息
     */
    public void initGroupDict(boolean reset){
        lock.writeLock().lock();
        try {
            log.info("开始初始化权限组数据信息......");
            List<RequestOriginBean> requestOriginBeans = initRequestOriginGroup();
            initGroupOptionModel(requestOriginBeans);
            initGroupApplyModel(requestOriginBeans);
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
            List<AuthGroupConfig> groupApply = authGroupConfigService.findGroupAndMemberByType(RubberGroupTypeEnums.apply);
            List<AuthGroupConfig> groupOption = authGroupConfigService.findGroupAndMemberByType(RubberGroupTypeEnums.option);
            for (RequestOriginBean requestOriginBean:requestOriginList){
                handleGroupAuthKey(requestOriginBean,groupApply,groupOption);
                String authKey = AuthorizeTools.createAuthKey(requestOriginBean.getApplyKey(),requestOriginBean.getOptionKey());
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
    private void initGroupOptionModel(List<RequestOriginBean> requestOriginList){
        if(CollUtil.isEmpty(requestOriginList)){
            return;
        }
        Map<String, List<RequestOriginBean>> collect = requestOriginList.stream().collect(Collectors.groupingBy(RequestOriginBean::getEffectiveOptionKey));
        for (String optionKey:collect.keySet()){
            List<RequestOriginBean> optionRequest = collect.get(optionKey);
            if(CollUtil.isEmpty(optionRequest)){
                continue;
            }
            GroupOptionApplyTreeModel optionTreeModel = new GroupOptionApplyTreeModel();
            optionTreeModel.setKey(optionRequest.get(0).getEffectiveOptionKey());
            optionTreeModel.setLabel(optionRequest.get(0).getEffectiveOptionName());


            Map<String, List<RequestOriginBean>> applyList = optionRequest.stream().collect(Collectors.groupingBy(RequestOriginBean::getEffectiveApplyKey));

            List<GroupOptionApplyTreeModel> applyTreeModelList = new ArrayList<>();
            for(String apply:applyList.keySet()){
                List<RequestOriginBean> applyOriginList = applyList.get(apply);
                if(CollUtil.isEmpty(applyOriginList)){
                    continue;
                }
                GroupOptionApplyTreeModel applyTreeModel = new GroupOptionApplyTreeModel();
                applyTreeModel.setKey(apply + AuthorizeKeys.AUTH_LINK_KEY + optionKey);
                applyTreeModel.setLabel(applyOriginList.get(0).getEffectiveApplyName());
                applyTreeModel.setRequestUrl(applyOriginList.stream().map(RequestOriginBean::getUrl).collect(Collectors.toSet()));

                applyTreeModelList.add(applyTreeModel);
            }
            optionTreeModel.setChildren(applyTreeModelList);
            allOptionTree.add(optionTreeModel);
        }
    }



    private void initGroupApplyModel(List<RequestOriginBean> requestOriginList){
        if(CollUtil.isEmpty(requestOriginList)){
            return;
        }
        Map<String, List<RequestOriginBean>> collect = requestOriginList.stream().collect(Collectors.groupingBy(RequestOriginBean::getEffectiveApplyKey));
        for (String applyKey:collect.keySet()){
            List<RequestOriginBean> applyRequest = collect.get(applyKey);
            if(CollUtil.isEmpty(applyRequest)){
                continue;
            }
            GroupOptionApplyTreeModel applyTreeModel = new GroupOptionApplyTreeModel();
            applyTreeModel.setKey(applyRequest.get(0).getEffectiveApplyKey());
            applyTreeModel.setLabel(applyRequest.get(0).getEffectiveApplyName());
            applyTreeModel.setMembers(applyRequest.get(0).getEffectiveApplyMembers());

            Map<String, List<RequestOriginBean>> optionList = applyRequest.stream().collect(Collectors.groupingBy(RequestOriginBean::getEffectiveOptionKey));
            List<GroupOptionApplyTreeModel> optionTreeModelList = new ArrayList<>();
            for(String option:optionList.keySet()){
                List<RequestOriginBean> optionOriginList = optionList.get(option);
                if(CollUtil.isEmpty(optionOriginList)){
                    continue;
                }

                GroupOptionApplyTreeModel optionTree = new GroupOptionApplyTreeModel();
                optionTree.setKey(applyKey + AuthorizeKeys.AUTH_LINK_KEY + option);
                optionTree.setLabel(optionOriginList.get(0).getEffectiveOptionName());
                optionTree.setMembers(optionOriginList.get(0).getEffectiveOptionMembers());
                optionTree.setRequestUrl(optionOriginList.stream().map(RequestOriginBean::getUrl).collect(Collectors.toSet()));
                optionTreeModelList.add(optionTree);
            }
            applyTreeModel.setChildren(optionTreeModelList);
            allApplyTree.add(applyTreeModel);
        }

    }




    /**
     * 映射族群
     * @param requestOriginBean
     * @param groupApply
     * @param groupOption
     */
    private void handleGroupAuthKey(RequestOriginBean requestOriginBean, List<AuthGroupConfig> groupApply, List<AuthGroupConfig> groupOption){

        AuthGroupConfig cGroup = authGroupConfigService.startWithByMember(groupApply, requestOriginBean.getApplyKey());
        if (cGroup != null){
            requestOriginBean.setApplyGroup(cGroup);
        }else {
            requestOriginBean.setApplyGroup(null);
        }
        AuthGroupConfig mGroup = authGroupConfigService.startWithByMember(groupOption, requestOriginBean.getOptionKey());
        if (mGroup != null){
            requestOriginBean.setOptionGroup(mGroup);
        }else {
            requestOriginBean.setOptionGroup(null);
        }
    }


}
