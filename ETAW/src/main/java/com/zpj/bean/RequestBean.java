package com.zpj.bean;

public class RequestBean<T> {
    String reqId;
    T reqParam;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public T getReqParam() {
        return reqParam;
    }

    public void setReqParam(T reqParam) {
        this.reqParam = reqParam;
    }
}
