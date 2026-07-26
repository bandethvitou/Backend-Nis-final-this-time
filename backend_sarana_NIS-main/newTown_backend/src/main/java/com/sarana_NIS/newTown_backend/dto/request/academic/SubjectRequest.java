package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SubjectRequest {

    @NotBlank(message = "Subject name is required")
    @Size(max = 100, message = "Subject name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Subject code is required")
    @Size(max = 20, message = "Subject code must be less than 20 characters")
    private String code;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private Long departmentId;

    private Integer credits;

    // Constructors
    public SubjectRequest() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}