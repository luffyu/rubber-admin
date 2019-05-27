package com.rubber.admin.core.page;

import java.io.Serializable;

/**
 * @author luffyu
 * Created on 2019-05-27
 */
public class CompareModel implements Serializable {

    private String field;

    private CompareType type;

    private Object data;

    private Object dateEnd;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Object dateEnd) {
        this.dateEnd = dateEnd;
    }

    public CompareType getType() {
        return type;
    }

    public void setType(CompareType type) {
        this.type = type;
    }
}
