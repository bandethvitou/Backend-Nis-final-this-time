package com.sarana_NIS.newTown_backend.dto.response.management;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagementDashboardResponse {

    private Integer totalTasks;
    private Integer pendingTasks;
    private Integer completedTasks;
    private Integer totalGoals;
    private Integer completedGoals;
    private Integer totalReports;
    private Integer pendingReports;
    private Integer totalProposals;
    private Integer pendingProposals;
    private List<TaskPriority> taskPriority = new ArrayList<>();
    private List<GoalProgress> goalProgress = new ArrayList<>();
    private List<RecentActivity> recentActivities = new ArrayList<>();
    private Double taskCompletionRate;
    private Double goalCompletionRate;

    // Constructors
    public ManagementDashboardResponse() {}

    // Getters and Setters
    public Integer getTotalTasks() { return totalTasks; }
    public void setTotalTasks(Integer totalTasks) { this.totalTasks = totalTasks; }

    public Integer getPendingTasks() { return pendingTasks; }
    public void setPendingTasks(Integer pendingTasks) { this.pendingTasks = pendingTasks; }

    public Integer getCompletedTasks() { return completedTasks; }
    public void setCompletedTasks(Integer completedTasks) { this.completedTasks = completedTasks; }

    public Integer getTotalGoals() { return totalGoals; }
    public void setTotalGoals(Integer totalGoals) { this.totalGoals = totalGoals; }

    public Integer getCompletedGoals() { return completedGoals; }
    public void setCompletedGoals(Integer completedGoals) { this.completedGoals = completedGoals; }

    public Integer getTotalReports() { return totalReports; }
    public void setTotalReports(Integer totalReports) { this.totalReports = totalReports; }

    public Integer getPendingReports() { return pendingReports; }
    public void setPendingReports(Integer pendingReports) { this.pendingReports = pendingReports; }

    public Integer getTotalProposals() { return totalProposals; }
    public void setTotalProposals(Integer totalProposals) { this.totalProposals = totalProposals; }

    public Integer getPendingProposals() { return pendingProposals; }
    public void setPendingProposals(Integer pendingProposals) { this.pendingProposals = pendingProposals; }

    public List<TaskPriority> getTaskPriority() { return taskPriority; }
    public void setTaskPriority(List<TaskPriority> taskPriority) { this.taskPriority = taskPriority; }

    public List<GoalProgress> getGoalProgress() { return goalProgress; }
    public void setGoalProgress(List<GoalProgress> goalProgress) { this.goalProgress = goalProgress; }

    public List<RecentActivity> getRecentActivities() { return recentActivities; }
    public void setRecentActivities(List<RecentActivity> recentActivities) { this.recentActivities = recentActivities; }

    public Double getTaskCompletionRate() { return taskCompletionRate; }
    public void setTaskCompletionRate(Double taskCompletionRate) { this.taskCompletionRate = taskCompletionRate; }

    public Double getGoalCompletionRate() { return goalCompletionRate; }
    public void setGoalCompletionRate(Double goalCompletionRate) { this.goalCompletionRate = goalCompletionRate; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TaskPriority {
        private String priority;
        private Integer count;
        private Double percentage;

        // Constructors
        public TaskPriority() {}

        // Getters and Setters
        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GoalProgress {
        private String goalTitle;
        private Integer progress;
        private String status;
        private LocalDate deadline;

        // Constructors
        public GoalProgress() {}

        // Getters and Setters
        public String getGoalTitle() { return goalTitle; }
        public void setGoalTitle(String goalTitle) { this.goalTitle = goalTitle; }

        public Integer getProgress() { return progress; }
        public void setProgress(Integer progress) { this.progress = progress; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDate getDeadline() { return deadline; }
        public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RecentActivity {
        private String type;
        private String description;
        private String user;
        private LocalDateTime timestamp;
        private String action;

        // Constructors
        public RecentActivity() {}

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
    }
}