package com.rubber.admin.framework;


import com.alibaba.fastjson.JSON;
import com.rubber.admin.core.page.CompareModel;
import com.rubber.admin.core.page.CompareType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public class Test {

    public static void main(String[] args) {
        List<CompareModel> compareModels = new ArrayList<>();

        CompareModel compareModel = new CompareModel();
        compareModel.setField("userId");
        compareModel.setData(1);

        compareModels.add(compareModel);

        System.out.println(JSON.toJSONString(compareModels));
    }

}
