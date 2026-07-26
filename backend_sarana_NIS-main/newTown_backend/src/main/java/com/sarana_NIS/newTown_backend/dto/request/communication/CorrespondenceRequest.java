package com.sarana_NIS.newTown_backend.dto.request.communication;

import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceFormat;
import com.sarana_NIS.newTown_backend.model.enums.CorrespondenceType;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CorrespondenceRequest {

    @NotNull(message = "Type is required")
    private CorrespondenceType type;

    @NotNull(message = "Format is required")
    private CorrespondenceFormat format;

    @NotBlank(message = "From/To is required")
    private String fromTo;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Content is required")
    private String content;

    private Priority priority;
    private String attachmentUrl;

    // Constructors
    public CorrespondenceRequest() {}

    public CorrespondenceRequest(CorrespondenceType type, CorrespondenceFormat format, String fromTo, String subject, String content) {
        this.type = type;
        this.format = format;
        this.fromTo = fromTo;
        this.subject = subject;
        this.content = content;
    }

    // Getters and Setters
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

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }
}