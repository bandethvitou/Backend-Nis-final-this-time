package com.sarana_NIS.newTown_backend.exception;

public class BusinessException extends RuntimeException {

    private final String errorCode;
    private final int status;
    private final Object[] args;

    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
        this.status = 400;
        this.args = null;
    }

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.status = 400;
        this.args = null;
    }

    public BusinessException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.args = null;
    }

    public BusinessException(String message, String errorCode, int status, Object[] args) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.args = args;
    }

    // Getters
    public String getErrorCode() { return errorCode; }
    public int getStatus() { return status; }
    public Object[] getArgs() { return args; }
}