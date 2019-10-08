package com.rubber.admin.core.plugins.select;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luffyu
 * Created on 2019-05-27
 */
@Data
public class SelectModel implements Serializable {

    private String field;

    private CompareType type;

    private Object data;

    private Object dateEnd;

}

