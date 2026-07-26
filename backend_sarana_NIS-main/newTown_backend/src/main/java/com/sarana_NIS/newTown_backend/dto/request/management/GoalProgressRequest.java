package com.sarana_NIS.newTown_backend.dto.request.management;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class GoalProgressRequest {

    @NotNull(message = "Goal ID is required")
    private Long goalId;

    @NotNull(message = "Current value is required")
    @PositiveOrZero(message = "Current value must be positive or zero")
    private BigDecimal current;

    // Constructors
    public GoalProgressRequest() {}

    public GoalProgressRequest(Long goalId, BigDecimal current) {
        this.goalId = goalId;
        this.current = current;
    }

    // Getters and Setters
    public Long getGoalId() { return goalId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }

    public BigDecimal getCurrent() { return current; }
    public void setCurrent(BigDecimal current) { this.current = current; }
}