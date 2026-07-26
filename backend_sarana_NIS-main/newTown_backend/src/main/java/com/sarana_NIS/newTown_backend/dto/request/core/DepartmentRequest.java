package com.sarana_NIS.newTown_backend.dto.request.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartmentRequest {
    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 100, message = "Department name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private Long headUserId;

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getHeadUserId() {
        return headUserId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeadUserId(Long headUserId) {
        this.headUserId = headUserId;
    }
}