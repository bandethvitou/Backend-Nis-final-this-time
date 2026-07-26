package com.sarana_NIS.newTown_backend.exception;

public class ForbiddenException extends RuntimeException {

    private final String errorCode;
    private final String requiredRole;
    private final String requiredPermission;

    public ForbiddenException(String message) {
        super(message);
        this.errorCode = "FORBIDDEN";
        this.requiredRole = null;
        this.requiredPermission = null;
    }

    public ForbiddenException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.requiredRole = null;
        this.requiredPermission = null;
    }

    public ForbiddenException(String message, String requiredRole, String requiredPermission) {
        super(message);
        this.errorCode = "FORBIDDEN";
        this.requiredRole = requiredRole;
        this.requiredPermission = requiredPermission;
    }

    // Getters
    public String getErrorCode() { return errorCode; }
    public String getRequiredRole() { return requiredRole; }
    public String getRequiredPermission() { return requiredPermission; }
}