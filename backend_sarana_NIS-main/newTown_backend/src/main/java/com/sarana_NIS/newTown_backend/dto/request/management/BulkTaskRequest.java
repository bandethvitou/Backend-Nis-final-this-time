package com.sarana_NIS.newTown_backend.dto.request.management;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

public class BulkTaskRequest {

    @NotEmpty(message = "Task IDs cannot be empty")
    private List<Long> taskIds = new ArrayList<>();

    @Pattern(regexp = "^(ASSIGN|COMPLETE|DELETE|UPDATE_PRIORITY)$",
            message = "Invalid action")
    private String action;

    private Long assignToUserId;
    private String priority;
    private String reason;

    // Constructors
    public BulkTaskRequest() {}

    public BulkTaskRequest(List<Long> taskIds, String action) {
        this.taskIds = taskIds;
        this.action = action;
    }

    // Getters and Setters
    public List<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Long> taskIds) {
        this.taskIds = taskIds;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getAssignToUserId() {
        return assignToUserId;
    }

    public void setAssignToUserId(Long assignToUserId) {
        this.assignToUserId = assignToUserId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}