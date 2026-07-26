package com.sarana_NIS.newTown_backend.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public class VerifyEmailRequest {

    @NotBlank(message = "Verification token is required")
    private String verificationToken;

    // Constructors
    public VerifyEmailRequest() {}

    public VerifyEmailRequest(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    // Getters and Setters
    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String verificationToken) { this.verificationToken = verificationToken; }
}