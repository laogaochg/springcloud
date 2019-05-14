package com.example.eureka.util;

/**
 * @Author: LaoGaoChuang
 * @Date : 2017/12/13 18:55
 */
public class ExecuteResult<T> {
    private T data;
    private boolean success = false;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
