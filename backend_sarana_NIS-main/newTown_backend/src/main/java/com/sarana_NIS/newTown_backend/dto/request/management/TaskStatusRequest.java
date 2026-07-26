package com.sarana_NIS.newTown_backend.dto.request.management;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskStatusRequest {

    @NotNull(message = "Task ID is required")
    private Long taskId;

    @NotNull(message = "Status is required")
    private String status;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    private Integer progressPercentage;
    private String completionNotes;

    // Constructors
    public TaskStatusRequest() {}

    public TaskStatusRequest(Long taskId, String status) {
        this.taskId = taskId;
        this.status = status;
    }

    // Getters and Setters
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getCompletionNotes() {
        return completionNotes;
    }

    public void setCompletionNotes(String completionNotes) {
        this.completionNotes = completionNotes;
    }
}