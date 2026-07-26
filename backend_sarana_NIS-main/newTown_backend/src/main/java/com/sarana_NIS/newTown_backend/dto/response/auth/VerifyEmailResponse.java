package com.sarana_NIS.newTown_backend.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyEmailResponse {

    private Boolean verified;
    private String message;
    private String email;
    private Boolean isEmailVerified;

    // Constructors
    public VerifyEmailResponse() {}

    public VerifyEmailResponse(Boolean verified, String message) {
        this.verified = verified;
        this.message = message;
    }

    // Getters and Setters
    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getIsEmailVerified() { return isEmailVerified; }
    public void setIsEmailVerified(Boolean isEmailVerified) { this.isEmailVerified = isEmailVerified; }
}