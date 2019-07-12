package com.zpj.bean;

/**
 * 返回ajax的bean类
 * @param <T>
 * @author 李沛昊
 */
public class ResponseBean<T> {
    String reqId;
    T resData;
    String message;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public T getResData() {
        return resData;
    }

    public void setResData(T resData) {
        this.resData = resData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
