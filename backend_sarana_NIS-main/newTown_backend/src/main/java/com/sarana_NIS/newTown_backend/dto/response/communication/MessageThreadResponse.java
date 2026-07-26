package com.sarana_NIS.newTown_backend.dto.response.communication;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageThreadResponse {

    private Long id;
    private String subject;
    private MessageInfo originalMessage;
    private List<ReplyInfo> replies = new ArrayList<>();
    private Integer totalMessages;
    private Boolean isRead;
    private LocalDateTime lastActivity;

    // Constructors
    public MessageThreadResponse() {}

    public MessageThreadResponse(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public MessageInfo getOriginalMessage() { return originalMessage; }
    public void setOriginalMessage(MessageInfo originalMessage) { this.originalMessage = originalMessage; }

    public List<ReplyInfo> getReplies() { return replies; }
    public void setReplies(List<ReplyInfo> replies) { this.replies = replies; }

    public Integer getTotalMessages() { return totalMessages; }
    public void setTotalMessages(Integer totalMessages) { this.totalMessages = totalMessages; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public LocalDateTime getLastActivity() { return lastActivity; }
    public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MessageInfo {
        private Long id;
        private String senderName;
        private String senderId;
        private String content;
        private LocalDateTime sentAt;
        private List<String> attachments = new ArrayList<>();

        // Constructors
        public MessageInfo() {}

        public MessageInfo(Long id, String senderName, String content) {
            this.id = id;
            this.senderName = senderName;
            this.content = content;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getSenderName() { return senderName; }
        public void setSenderName(String senderName) { this.senderName = senderName; }

        public String getSenderId() { return senderId; }
        public void setSenderId(String senderId) { this.senderId = senderId; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public LocalDateTime getSentAt() { return sentAt; }
        public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

        public List<String> getAttachments() { return attachments; }
        public void setAttachments(List<String> attachments) { this.attachments = attachments; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReplyInfo {
        private Long id;
        private String senderName;
        private String content;
        private LocalDateTime sentAt;
        private Boolean isRead;

        // Constructors
        public ReplyInfo() {}

        public ReplyInfo(Long id, String senderName, String content) {
            this.id = id;
            this.senderName = senderName;
            this.content = content;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getSenderName() { return senderName; }
        public void setSenderName(String senderName) { this.senderName = senderName; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public LocalDateTime getSentAt() { return sentAt; }
        public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

        public Boolean getIsRead() { return isRead; }
        public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    }
}