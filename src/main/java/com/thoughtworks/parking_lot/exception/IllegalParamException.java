package com.thoughtworks.parking_lot.exception;

public class IllegalParamException extends RuntimeException {

    private Integer code;
    private String errMessage;

    public IllegalParamException(Integer code, String errMessage) {
        this.code = code;
        this.errMessage = errMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
