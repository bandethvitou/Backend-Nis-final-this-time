package com.sarana_NIS.newTown_backend.exception;

import java.time.LocalDateTime;

public class AccountLockedException extends RuntimeException {

    private final String username;
    private final LocalDateTime lockedUntil;
    private final Integer failedAttempts;
    private final String errorCode;

    public AccountLockedException(String message) {
        super(message);
        this.username = null;
        this.lockedUntil = null;
        this.failedAttempts = null;
        this.errorCode = "ACCOUNT_LOCKED";
    }

    public AccountLockedException(String message, String username) {
        super(message);
        this.username = username;
        this.lockedUntil = null;
        this.failedAttempts = null;
        this.errorCode = "ACCOUNT_LOCKED";
    }

    public AccountLockedException(String message, String username, LocalDateTime lockedUntil, Integer failedAttempts) {
        super(message);
        this.username = username;
        this.lockedUntil = lockedUntil;
        this.failedAttempts = failedAttempts;
        this.errorCode = "ACCOUNT_LOCKED";
    }

    // Getters
    public String getUsername() { return username; }
    public LocalDateTime getLockedUntil() { return lockedUntil; }
    public Integer getFailedAttempts() { return failedAttempts; }
    public String getErrorCode() { return errorCode; }
}