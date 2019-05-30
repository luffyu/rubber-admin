package com.rubber.admin.core.model;

import com.rubber.admin.core.enums.MsgCode;

/**
 * @author luffyu
 * Created on 2019-05-13
 */
public class ResultModel {

    /**
     * code
     */
    private String code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据信息
     */
    private Object data;

    public ResultModel() {
    }

    public ResultModel(MsgCode msgCode) {
        this.code = msgCode.code;
        this.msg = msgCode.msg;
    }

    public ResultModel(MsgCode msgCode, Object data) {
        this.code = msgCode.code;
        this.msg = msgCode.msg;
        this.data = data;
    }

    public ResultModel(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultModel(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 返回成功
     * @return 返回一个成功的ResultModel
     */
    public static ResultModel createSuccess(){
        return new ResultModel(MsgCode.SUCCESS);
    }
    public static ResultModel createSuccess(Object data){
        return new ResultModel(MsgCode.SUCCESS,data);
    }

    /**
     * 返回失败的信息
     * @param msgCode 错误码
     * @param data 数据信息
     * @return 返回一个错误的code
     */
    public static ResultModel createError(MsgCode msgCode,Object data){
        return new ResultModel(msgCode,data);
    }

    public static ResultModel createError(MsgCode msgCode){
        return createError(msgCode,null);
    }


    public static ResultModel createError(String code,String msg,Object data){
        return new ResultModel(code,msg,data);
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
