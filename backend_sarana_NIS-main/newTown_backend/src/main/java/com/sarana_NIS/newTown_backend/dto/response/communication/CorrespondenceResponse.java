package com.sarana_NIS.newTown_backend.dto.response.communication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceFormat;
import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceStatus;
import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceType;
import com.sarana_NIS.newTown_backend.model.enums.Priority;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrespondenceResponse {

    private Long id;  // ✅ This field exists
    private CorrespondenceType type;
    private CorrespondenceFormat format;
    private String fromTo;
    private String subject;
    private String content;
    private Priority priority;
    private CorrespondenceStatus status;
    private LocalDate date;
    private String attachmentUrl;

    // Created By
    private String createdBy;
    private Long createdById;
    private String createdByName;

    private Integer daysSince;
    private Boolean isUrgent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public CorrespondenceResponse() {}

    public CorrespondenceResponse(Long id, String subject, String fromTo) {
        this.id = id;
        this.subject = subject;
        this.fromTo = fromTo;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CorrespondenceType getType() { return type; }
    public void setType(CorrespondenceType type) { this.type = type; }

    public CorrespondenceFormat getFormat() { return format; }
    public void setFormat(CorrespondenceFormat format) { this.format = format; }

    public String getFromTo() { return fromTo; }
    public void setFromTo(String fromTo) { this.fromTo = fromTo; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public CorrespondenceStatus getStatus() { return status; }
    public void setStatus(CorrespondenceStatus status) { this.status = status; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }

    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }

    public Integer getDaysSince() { return daysSince; }
    public void setDaysSince(Integer daysSince) { this.daysSince = daysSince; }

    public Boolean getIsUrgent() { return isUrgent; }
    public void setIsUrgent(Boolean isUrgent) { this.isUrgent = isUrgent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}