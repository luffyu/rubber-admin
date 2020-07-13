package com.rubber.admin.core.authorize;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.rubber.admin.core.authorize.entity.AuthGroupConfig;
import com.rubber.admin.core.authorize.model.GroupOptionApplyTreeModel;
import com.rubber.admin.core.authorize.model.RequestOriginBean;
import com.rubber.admin.core.authorize.model.RubberGroupTypeEnums;
import com.rubber.admin.core.authorize.service.IAuthGroupConfigService;
import com.rubber.admin.core.system.entity.SysUser;
import com.rubber.admin.core.system.service.ISysUserService;
import com.rubber.admin.core.tools.RubberTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * <p>
 *  1、ContextRefreshedEvent
 * ApplicationContext 被初始化或刷新时，该事件被发布。这也可以在 ConfigurableApplicationContext接口中使用 refresh() 方法来发生。此处的初始化是指：所有的Bean被成功装载，后处理Bean被检测并激活，所有Singleton Bean 被预实例化，ApplicationContext容器已就绪可用
 *
 * 2、ContextStartedEvent
 * 当使用 ConfigurableApplicationContext （ApplicationContext子接口）接口中的 start() 方法启动 ApplicationContext 时，该事件被发布。你可以调查你的数据库，或者你可以在接受到这个事件后重启任何停止的应用程序
 *
 * 3、ContextStoppedEvent
 * 当使用 ConfigurableApplicationContext 接口中的 stop() 停止 ApplicationContext 时，发布这个事件。你可以在接受到这个事件后做必要的清理的工作
 *
 * 4、ContextClosedEvent
 * 当使用 ConfigurableApplicationContext 接口中的 close() 方法关闭 ApplicationContext 时，该事件被发布。一个已关闭的上下文到达生命周期末端；它不能被刷新或重启
 *
 * 5、RequestHandledEvent
 * 这是一个 web-specific 事件，告诉所有 bean HTTP 请求已经被服务。只能应用于使用DispatcherServlet的Web应用。在使用Spring作为前端的MVC控制器时，当Spring处理用户请求结束后，系统会自动触发该事件
 * </p>
 *
 *
 *
 * 对原始对URL数据进行族群分类、聚合
 * 1：生成每个url对应对权限关系
 * 2：生成 相关权限对业务权限和操作权限
 *
 * @author luffyu
 * @date 2020-03-12 20:15
 **/
@Slf4j
@Component
public class RubberAuthorizeGroupContext implements ApplicationListener<ContextRefreshedEvent>{

    /**
     * 当前系统中的全部url请求所需要的权限信息
     */
    private static Map<String,String> urlAuthDict = new ConcurrentHashMap<>(128);


    /**
     * 当前操作的树形结构
     */
    private static List<GroupOptionApplyTreeModel> allOptionTree = new ArrayList<>();

    /**
     * 当前业务所需要的树形结构
     */
    private static List<GroupOptionApplyTreeModel> allApplyTree = new ArrayList<>();


    /**
     * 读写锁
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * 族群配置中心
     */
    @Resource
    IAuthGroupConfigService authGroupConfigService;

    /**
     * 当前的用户service
     */
    @Resource
    private ISysUserService iSysUserService;


    /**
     * url的比较方法
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        RubberTools.print();
        initGroupDict(false);
    }


    /**
     * 初始化数据信息
     */
    public void initGroupDict(boolean reset){
        lock.writeLock().lock();
        try {
            if (reset){
                clearAll();
            }
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
     * 清空所有的数据信息
     */
    private void clearAll(){
        urlAuthDict.clear();
        allOptionTree.clear();
        allApplyTree.clear();
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
                String authKey = AuthorizeTools.createAuthKey(requestOriginBean.getEffectiveApplyKey(),requestOriginBean.getEffectiveOptionKey());
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


    /**
     * 获取权限信息
     * @param httpServletRequest 当前的http请求
     * @return 返回当前请求的权限字段
     */
    public String getUrlAuthorizeKey(HttpServletRequest httpServletRequest){
        String url = httpServletRequest.getServletPath();
        String authKey = urlAuthDict.get(url);
        if (StrUtil.isBlank(authKey)){
             authKey = getAuthKeyByMatchUrl(url);
        }
        return authKey;
    }

    /**
     * 获取全部的操作树结构
     * @return  获取全部的操作树结构
     */
    public List<GroupOptionApplyTreeModel> getAllOptionTree() {
        return allOptionTree;
    }

    /**
     * 获取全部的操作业务结构
     * @return 获取全部的操作业务结构
     */
    public List<GroupOptionApplyTreeModel> getAllApplyTree() {
        return allApplyTree;
    }


    /**
     * 权限认证
     * @param httpServletRequest 当前的http请求
     * @param sysUser 当前的登陆用户
     * @return 返回用户的基本信息
     */
    public boolean verifyUserRequestAuthorize(HttpServletRequest httpServletRequest, SysUser sysUser){
        if (sysUser.getSuperUser() != null && sysUser.getSuperUser() ==  AuthorizeTools.SUPER_ADMIN_FLAG){
            return true;
        }
        String urlAuthorizeKey = getUrlAuthorizeKey(httpServletRequest);
        Set<String> authorizeKeys = iSysUserService.getAuthorizeKeys(sysUser.getUserId());
        if (CollUtil.isEmpty(authorizeKeys)){
            return false;
        }
        return authorizeKeys.contains(urlAuthorizeKey);
    }


    /**
     * 比较url的匹配controller
     * @param url 当前比较的url
     * @return 返回权限值
     */
    private String getAuthKeyByMatchUrl(String url){
        for (Map.Entry<String,String> data: urlAuthDict.entrySet()){
            String key = data.getKey();
            if (antPathMatcher.match(key,url)){
                return data.getValue();
            }
        }
        return null;
    }

}
