package com.sarana_NIS.newTown_backend.dto.response.management;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskAssignmentResponse {

    private Long id;
    private String title;
    private String description;
    private UserInfo assignedBy;
    private List<AssigneeInfo> assignees = new ArrayList<>();
    private LocalDate dueDate;
    private String priority;
    private String status;
    private LocalDateTime createdAt;
    private List<String> attachments = new ArrayList<>();

    // Constructors
    public TaskAssignmentResponse() {}

    public TaskAssignmentResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UserInfo getAssignedBy() { return assignedBy; }
    public void setAssignedBy(UserInfo assignedBy) { this.assignedBy = assignedBy; }

    public List<AssigneeInfo> getAssignees() { return assignees; }
    public void setAssignees(List<AssigneeInfo> assignees) { this.assignees = assignees; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<String> getAttachments() { return attachments; }
    public void setAttachments(List<String> attachments) { this.attachments = attachments; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserInfo {
        private Long id;
        private String name;
        private String email;
        private String department;

        // Constructors
        public UserInfo() {}

        public UserInfo(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AssigneeInfo {
        private Long id;
        private String name;
        private String email;
        private String status;
        private LocalDateTime respondedAt;
        private String notes;

        // Constructors
        public AssigneeInfo() {}

        public AssigneeInfo(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDateTime getRespondedAt() { return respondedAt; }
        public void setRespondedAt(LocalDateTime respondedAt) { this.respondedAt = respondedAt; }

        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }
}