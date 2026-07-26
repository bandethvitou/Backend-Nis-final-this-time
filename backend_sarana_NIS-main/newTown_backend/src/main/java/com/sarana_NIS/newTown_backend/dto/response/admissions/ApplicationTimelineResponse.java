package com.sarana_NIS.newTown_backend.dto.response.admissions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationTimelineResponse {

    private Long applicationId;
    private String studentName;
    private String parentName;
    private String parentPhone;
    private String status;
    private Boolean isCompleted;
    private List<TimelineEvent> events = new ArrayList<>();

    // Constructors
    public ApplicationTimelineResponse() {}

    public ApplicationTimelineResponse(Long applicationId, String studentName) {
        this.applicationId = applicationId;
        this.studentName = studentName;
    }

    // Getters and Setters
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    public List<TimelineEvent> getEvents() { return events; }
    public void setEvents(List<TimelineEvent> events) { this.events = events; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TimelineEvent {
        private String stage;
        private String description;
        private LocalDateTime timestamp;
        private Boolean isComplete;

        // Constructors
        public TimelineEvent() {}

        public TimelineEvent(String stage, String description, LocalDateTime timestamp) {
            this.stage = stage;
            this.description = description;
            this.timestamp = timestamp;
        }

        // Getters and Setters
        public String getStage() { return stage; }
        public void setStage(String stage) { this.stage = stage; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public Boolean getIsComplete() { return isComplete; }
        public void setIsComplete(Boolean isComplete) { this.isComplete = isComplete; }
    }
}