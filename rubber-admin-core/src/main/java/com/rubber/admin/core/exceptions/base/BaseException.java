package com.rubber.admin.core.exceptions.base;

import com.rubber.admin.core.enums.MsgCode;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public class BaseException extends RuntimeException {


    private String code;

    private String msg;

    private Object data;

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public BaseException(MsgCode msgCode,Object data) {
        this.code = msgCode.code;
        this.msg = msgCode.msg;
        this.data = data;
    }


    public BaseException(MsgCode msgCode) {
        this.code = msgCode.code;
        this.msg = msgCode.msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
