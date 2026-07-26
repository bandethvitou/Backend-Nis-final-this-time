package com.sarana_NIS.newTown_backend.dto.response.management;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoalDashboardResponse {

    private Integer totalGoals;
    private Integer notStarted;
    private Integer inProgress;
    private Integer completed;
    private Integer overdue;
    private Double overallProgress;
    private List<GoalResponse> recentGoals = new ArrayList<>();

    // Constructors
    public GoalDashboardResponse() {}

    public GoalDashboardResponse(Integer totalGoals, Double overallProgress) {
        this.totalGoals = totalGoals;
        this.overallProgress = overallProgress;
    }

    // Getters and Setters
    public Integer getTotalGoals() { return totalGoals; }
    public void setTotalGoals(Integer totalGoals) { this.totalGoals = totalGoals; }

    public Integer getNotStarted() { return notStarted; }
    public void setNotStarted(Integer notStarted) { this.notStarted = notStarted; }

    public Integer getInProgress() { return inProgress; }
    public void setInProgress(Integer inProgress) { this.inProgress = inProgress; }

    public Integer getCompleted() { return completed; }
    public void setCompleted(Integer completed) { this.completed = completed; }

    public Integer getOverdue() { return overdue; }
    public void setOverdue(Integer overdue) { this.overdue = overdue; }

    public Double getOverallProgress() { return overallProgress; }
    public void setOverallProgress(Double overallProgress) { this.overallProgress = overallProgress; }

    public List<GoalResponse> getRecentGoals() { return recentGoals; }
    public void setRecentGoals(List<GoalResponse> recentGoals) { this.recentGoals = recentGoals; }
}