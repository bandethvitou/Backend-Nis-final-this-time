package com.sarana_NIS.newTown_backend.dto.request.communication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MessageThreadRequest {

    @NotNull(message = "Parent message ID is required")
    private Long parentMessageId;

    @NotBlank(message = "Reply content is required")
    private String content;

    // Constructors
    public MessageThreadRequest() {}

    public MessageThreadRequest(Long parentMessageId, String content) {
        this.parentMessageId = parentMessageId;
        this.content = content;
    }

    // Getters and Setters
    public Long getParentMessageId() { return parentMessageId; }
    public void setParentMessageId(Long parentMessageId) { this.parentMessageId = parentMessageId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}