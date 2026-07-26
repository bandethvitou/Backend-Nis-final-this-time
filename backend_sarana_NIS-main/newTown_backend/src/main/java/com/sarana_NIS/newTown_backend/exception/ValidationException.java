package com.sarana_NIS.newTown_backend.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {

    private final Map<String, String> validationErrors;
    private final String errorCode;

    public ValidationException(String message) {
        super(message);
        this.validationErrors = null;
        this.errorCode = "VALIDATION_ERROR";
    }

    public ValidationException(String message, Map<String, String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
        this.errorCode = "VALIDATION_ERROR";
    }

    public ValidationException(String message, Map<String, String> validationErrors, String errorCode) {
        super(message);
        this.validationErrors = validationErrors;
        this.errorCode = errorCode;
    }

    // Getters
    public Map<String, String> getValidationErrors() { return validationErrors; }
    public String getErrorCode() { return errorCode; }
}