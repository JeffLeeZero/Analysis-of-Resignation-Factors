package com.zpj.bean;

public class LoginBean {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    private String message = null;
    private boolean isSuccess = false;

    public LoginBean(String m, boolean s){
        this.message=m;
        this.isSuccess=s;
    }



}
