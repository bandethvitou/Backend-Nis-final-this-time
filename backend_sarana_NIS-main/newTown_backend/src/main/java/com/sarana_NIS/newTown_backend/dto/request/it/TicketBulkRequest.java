package com.sarana_NIS.newTown_backend.dto.request.it;

import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.TicketCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TicketBulkRequest {

    @NotEmpty(message = "At least one user ID is required")
    private List<Long> userIds = new ArrayList<>();

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Category is required")
    private TicketCategory category;

    @NotNull(message = "Priority is required")
    private Priority priority;

    private String location;

    // Constructors
    public TicketBulkRequest() {}

    public TicketBulkRequest(List<Long> userIds, String title, TicketCategory category, Priority priority) {
        this.userIds = userIds;
        this.title = title;
        this.category = category;
        this.priority = priority;
    }

    // ========== Getters and Setters ==========

    public List<Long> getUserIds() { return userIds; }
    public void setUserIds(List<Long> userIds) { this.userIds = userIds; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TicketCategory getCategory() { return category; }
    public void setCategory(TicketCategory category) { this.category = category; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    // ✅ ADD THIS METHOD if getAssignedToIds() is used
    public List<Long> getAssignedToIds() {
        return userIds; // or return a separate list if needed
    }

    public void setAssignedToIds(List<Long> assignedToIds) {
        this.userIds = assignedToIds;
    }
}