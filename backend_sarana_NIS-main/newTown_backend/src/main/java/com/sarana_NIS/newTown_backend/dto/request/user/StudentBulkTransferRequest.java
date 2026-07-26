package com.sarana_NIS.newTown_backend.dto.request.user;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class StudentBulkTransferRequest {

    @NotNull(message = "Student IDs are required")
    private List<Long> studentIds;

    @NotNull(message = "New class ID is required")
    private Long newClassId;

    private LocalDate transferDate;

    // Constructors
    public StudentBulkTransferRequest() {
    }

    // Getters and Setters
    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public Long getNewClassId() {
        return newClassId;
    }

    public void setNewClassId(Long newClassId) {
        this.newClassId = newClassId;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }
}