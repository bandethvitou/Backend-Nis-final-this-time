package com.sarana_NIS.newTown_backend.dto.request.communication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class MessageBulkRequest {

    @NotEmpty(message = "At least one receiver is required")
    private List<Long> receiverIds = new ArrayList<>();

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Content is required")
    private String content;

    // Constructors
    public MessageBulkRequest() {}

    public MessageBulkRequest(List<Long> receiverIds, String subject, String content) {
        this.receiverIds = receiverIds;
        this.subject = subject;
        this.content = content;
    }

    // Getters and Setters
    public List<Long> getReceiverIds() { return receiverIds; }
    public void setReceiverIds(List<Long> receiverIds) { this.receiverIds = receiverIds; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}