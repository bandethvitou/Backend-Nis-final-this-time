package com.sarana_NIS.newTown_backend.dto.request.academic;

import com.sarana_NIS.newTown_backend.model.enums.Shift;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class LessonPlanRequest {

    @NotNull(message = "Class Subject ID is required")
    private Long classSubjectId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Shift is required")
    private Shift shift;

    @NotBlank(message = "Topic is required")
    @Size(min = 2, max = 200, message = "Topic must be between 2 and 200 characters")
    private String topic;

    @Size(max = 500, message = "Objective must be less than 500 characters")
    private String objective;

    private String activities;
    private Boolean homeworkAssigned;

    // Constructors
    public LessonPlanRequest() {}

    public LessonPlanRequest(Long classSubjectId, LocalDate date, String topic) {
        this.classSubjectId = classSubjectId;
        this.date = date;
        this.topic = topic;
    }

    // Getters and Setters
    public Long getClassSubjectId() { return classSubjectId; }
    public void setClassSubjectId(Long classSubjectId) { this.classSubjectId = classSubjectId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public String getActivities() { return activities; }
    public void setActivities(String activities) { this.activities = activities; }

    public Boolean getHomeworkAssigned() { return homeworkAssigned; }
    public void setHomeworkAssigned(Boolean homeworkAssigned) { this.homeworkAssigned = homeworkAssigned; }
}