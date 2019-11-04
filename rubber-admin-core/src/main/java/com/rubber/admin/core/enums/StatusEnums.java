package com.rubber.admin.core.enums;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public interface StatusEnums {

    /**
     * 完成状态
     */
    int FINISH = 2;

    /**
     * 锁定 /上架状态
     */
    int LOCK = 1;

    /**
     * 正常可用的key值
     * 正常状态
     */
    int NORMAL = 0;

    /**
     * 不可用
     */
    int DISABLE = -1;

    /**
     * 删除状态
     */
    int DELETE = -1;

}
