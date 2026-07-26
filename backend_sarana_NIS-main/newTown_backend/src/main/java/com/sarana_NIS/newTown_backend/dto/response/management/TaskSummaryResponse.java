package com.sarana_NIS.newTown_backend.dto.response.management;

public class TaskSummaryResponse {
    private Integer totalTasks;
    private Integer pending;
    private Integer completed;
    private Integer overdue;
    private Integer highPriority;
    private Integer mediumPriority;
    private Integer lowPriority;

    // Constructors
    public TaskSummaryResponse() {
        this.totalTasks = 0;
        this.pending = 0;
        this.completed = 0;
        this.overdue = 0;
        this.highPriority = 0;
        this.mediumPriority = 0;
        this.lowPriority = 0;
    }

    // Getters and Setters
    public Integer getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Integer totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getOverdue() {
        return overdue;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
    }

    public Integer getHighPriority() {
        return highPriority;
    }

    public void setHighPriority(Integer highPriority) {
        this.highPriority = highPriority;
    }

    public Integer getMediumPriority() {
        return mediumPriority;
    }

    public void setMediumPriority(Integer mediumPriority) {
        this.mediumPriority = mediumPriority;
    }

    public Integer getLowPriority() {
        return lowPriority;
    }

    public void setLowPriority(Integer lowPriority) {
        this.lowPriority = lowPriority;
    }
}