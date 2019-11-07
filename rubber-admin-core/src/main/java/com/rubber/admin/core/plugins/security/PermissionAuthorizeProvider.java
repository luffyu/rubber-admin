package com.rubber.admin.core.plugins.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.rubber.admin.core.system.entity.SysPermissionDict;
import com.rubber.admin.core.system.model.PermissionDictModel;
import com.rubber.admin.core.system.service.ISysPermissionDictService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luffyu
 * Created on 2019-11-07
 */
public class PermissionAuthorizeProvider {


    /**
     * url的权限字典
     * key表示urlPath
     * value表示该url需要的权限字段key
     */
    private static Map<String,String> urlPermissionDict =  new ConcurrentHashMap<>(100);


    /**
     * 全部的字典值
     */
    private static Map<String, PermissionDictModel> allPermissionDictModel = new ConcurrentHashMap<>(100);


    /**
     * 返回请求的对照表字典
     * @return
     */
    public static Map<String, String> getUrlPermissionDict() {
        if(MapUtil.isEmpty(allPermissionDictModel)){
            // TODO: 2019-11-07 需要加锁
            createUrlPermissionDict();
        }
        return urlPermissionDict;
    }


    public static Map<String, PermissionDictModel> getAllPermissionDictModel() {
        if(MapUtil.isEmpty(allPermissionDictModel)){
            // TODO: 2019-11-07 需要加锁
            createUrlPermissionDict();
        }
        return allPermissionDictModel;
    }


    /**
     * 清除缓存信息
     * @return 返回是否清除成功
     */
    public static boolean clearCache(){
        if(urlPermissionDict != null){
            urlPermissionDict.clear();
        }
        if(allPermissionDictModel != null){
            allPermissionDictModel.clear();
        }
        return true;
    }


    /**
     * 创建url对照表的权限列表信息
     * @return 返回全部的权限信息
     */
    public static void createUrlPermissionDict() {
        clearCache();

        List<MappingUrlOriginBean> mappingOriginBeans = MappingUrlOriginHandler.getMappingOriginBeans();
        if(CollectionUtil.isNotEmpty(mappingOriginBeans)){
            ISysPermissionDictService permissionDict = MappingUrlOriginHandler.getApplicationContext().getBean(ISysPermissionDictService.class);

            //获取模块的目录信息
            List<SysPermissionDict> privilegeModuleDicts = permissionDict.selectByType(PermissionUtils.BASIC_MODULE);
            //获取Unit的目录信息
            List<SysPermissionDict> privilegeUnitDicts = permissionDict.selectByType(PermissionUtils.BASIC_UNIT);

            for (MappingUrlOriginBean mappingUrlOriginBean : mappingOriginBeans) {
                String moduleKey = mappingUrlOriginBean.getModule();
                String unitKey = mappingUrlOriginBean.getUnit();

                SysPermissionDict moduleDict = PermissionUtils.findByValue(moduleKey, privilegeModuleDicts);
                SysPermissionDict unitKeyDict = PermissionUtils.findByValue(unitKey, privilegeUnitDicts);
                moduleKey = moduleDict==null?moduleKey:moduleDict.getDictKey();
                unitKey = unitKeyDict==null?PermissionUtils.DEFAULT_UNIT_KEY:unitKeyDict.getDictKey();

                doWriteUrlPermissionDict(mappingUrlOriginBean.getUrl(),moduleKey,unitKey);
                doWritePermissionDictModel(moduleKey,unitKey,moduleDict,unitKeyDict);
            }
        }
    }



    private static void doWriteUrlPermissionDict(String url,String moduleKey,String unitKey){
        urlPermissionDict.putIfAbsent(url,PermissionUtils.createAuthorizeKey(moduleKey,unitKey));
    }
    private static void doWritePermissionDictModel(String moduleKey,String unitKey, SysPermissionDict moduleDict, SysPermissionDict unitKeyDict){
        PermissionDictModel dictModel = allPermissionDictModel.get(moduleKey);
        if(dictModel == null){
            dictModel = new PermissionDictModel(moduleKey,moduleDict);
        }
        Map<String, PermissionDictModel> dictModelUnitKey = dictModel.getUnitKey();
        if(dictModelUnitKey == null){
            dictModelUnitKey = new ConcurrentHashMap<>(10);
        }
        PermissionDictModel unitDict = dictModelUnitKey.get(unitKey);
        if(unitDict == null){
            unitDict = new PermissionDictModel(unitKey,unitKeyDict);
        }
        dictModelUnitKey.putIfAbsent(unitKey,unitDict);
        dictModel.setUnitKey(dictModelUnitKey);

        allPermissionDictModel.putIfAbsent(moduleKey,dictModel);
    }




}
