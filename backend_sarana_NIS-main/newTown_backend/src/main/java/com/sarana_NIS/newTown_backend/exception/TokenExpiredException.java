package com.sarana_NIS.newTown_backend.exception;

public class TokenExpiredException extends RuntimeException {

    private final String token;
    private final String errorCode;

    public TokenExpiredException(String message) {
        super(message);
        this.token = null;
        this.errorCode = "TOKEN_EXPIRED";
    }

    public TokenExpiredException(String message, String token) {
        super(message);
        this.token = token;
        this.errorCode = "TOKEN_EXPIRED";
    }

    public TokenExpiredException(String message, String token, String errorCode) {
        super(message);
        this.token = token;
        this.errorCode = errorCode;
    }

    // Getters
    public String getToken() { return token; }
    public String getErrorCode() { return errorCode; }
}