package com.sarana_NIS.newTown_backend.dto.response.management;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoalProgressResponse {

    private Long goalId;
    private String title;
    private BigDecimal target;
    private BigDecimal current;
    private Integer percentage;
    private GoalStatus status;
    private LocalDate deadline;
    private String trend;

    // Constructors
    public GoalProgressResponse() {}

    public GoalProgressResponse(Long goalId, String title, BigDecimal target, BigDecimal current) {
        this.goalId = goalId;
        this.title = title;
        this.target = target;
        this.current = current;
    }

    // Getters and Setters
    public Long getGoalId() { return goalId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getTarget() { return target; }
    public void setTarget(BigDecimal target) { this.target = target; }

    public BigDecimal getCurrent() { return current; }
    public void setCurrent(BigDecimal current) { this.current = current; }

    public Integer getPercentage() { return percentage; }
    public void setPercentage(Integer percentage) { this.percentage = percentage; }

    public GoalStatus getStatus() { return status; }
    public void setStatus(GoalStatus status) { this.status = status; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getTrend() { return trend; }
    public void setTrend(String trend) { this.trend = trend; }
}