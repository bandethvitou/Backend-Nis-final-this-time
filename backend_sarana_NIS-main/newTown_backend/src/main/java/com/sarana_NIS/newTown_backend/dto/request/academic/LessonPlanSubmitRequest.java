package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LessonPlanSubmitRequest {

    @NotNull(message = "Lesson plan ID is required")
    private Long lessonPlanId;

    @Pattern(regexp = "^(MANAGER|TOP_MANAGER)$", message = "Submit to must be MANAGER or TOP_MANAGER")
    private String submitTo;

    @Size(max = 500, message = "Revision comment must be less than 500 characters")
    private String revisionComment;

    @Pattern(regexp = "^(SUBMIT|APPROVE|REVISE|REJECT)$", message = "Invalid action")
    private String action;

    // Constructors
    public LessonPlanSubmitRequest() {}

    public LessonPlanSubmitRequest(Long lessonPlanId, String action) {
        this.lessonPlanId = lessonPlanId;
        this.action = action;
    }

    // Getters and Setters
    public Long getLessonPlanId() { return lessonPlanId; }
    public void setLessonPlanId(Long lessonPlanId) { this.lessonPlanId = lessonPlanId; }

    public String getSubmitTo() { return submitTo; }
    public void setSubmitTo(String submitTo) { this.submitTo = submitTo; }

    public String getRevisionComment() { return revisionComment; }
    public void setRevisionComment(String revisionComment) { this.revisionComment = revisionComment; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}