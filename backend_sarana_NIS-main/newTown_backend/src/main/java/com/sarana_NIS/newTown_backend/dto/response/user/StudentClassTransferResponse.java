package com.sarana_NIS.newTown_backend.dto.response.user;

import java.time.LocalDateTime;
import java.util.List;

public class StudentClassTransferResponse {
    private boolean success;
    private String message;
    private LocalDateTime transferDate;
    private List<String> warnings;

    // Constructors
    public StudentClassTransferResponse() {
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}