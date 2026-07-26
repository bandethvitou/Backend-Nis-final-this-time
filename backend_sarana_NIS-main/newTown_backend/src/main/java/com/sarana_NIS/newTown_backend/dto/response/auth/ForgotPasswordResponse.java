package com.sarana_NIS.newTown_backend.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForgotPasswordResponse {

    private Boolean success;
    private String message;
    private String resetToken;
    private String email;

    // Constructors
    public ForgotPasswordResponse() {}

    public ForgotPasswordResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}