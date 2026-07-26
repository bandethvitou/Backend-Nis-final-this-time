package com.sarana_NIS.newTown_backend.dto.request.management;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GoalRequest {

    @NotBlank(message = "Goal title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Target is required")
    @Positive(message = "Target must be positive")
    private BigDecimal target;

    @Size(max = 50, message = "Unit must be less than 50 characters")
    private String unit;

    @NotNull(message = "Deadline is required")
    private LocalDate deadline;

    @NotNull(message = "Manager ID is required")
    private Long managerId;

    // Constructors
    public GoalRequest() {}

    public GoalRequest(String title, BigDecimal target, LocalDate deadline, Long managerId) {
        this.title = title;
        this.target = target;
        this.deadline = deadline;
        this.managerId = managerId;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getTarget() { return target; }
    public void setTarget(BigDecimal target) { this.target = target; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
}