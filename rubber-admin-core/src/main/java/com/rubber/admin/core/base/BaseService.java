package com.rubber.admin.core.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubber.admin.core.model.PagerModel;

/**
 * @author luffyu
 * Created on 2019-05-11
 */
public class BaseService<M extends BaseMapper<T>,T extends BaseEntity> extends ServiceImpl<M,T> {
}
