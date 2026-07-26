package com.sarana_NIS.newTown_backend.dto.request.auth;

public class LogoutRequest {

    private String refreshToken;

    // Constructors
    public LogoutRequest() {}

    public LogoutRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getters and Setters
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}