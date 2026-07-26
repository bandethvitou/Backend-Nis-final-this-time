package com.sarana_NIS.newTown_backend.dto.response.communication;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnreadMessage {

    private Long totalUnread;
    private List<UnreadMessageInfo> messages = new ArrayList<>();

    // Constructors
    public UnreadMessage() {}

    public UnreadMessage(Long totalUnread) {
        this.totalUnread = totalUnread;
    }

    // Getters and Setters
    public Long getTotalUnread() { return totalUnread; }
    public void setTotalUnread(Long totalUnread) { this.totalUnread = totalUnread; }

    public List<UnreadMessageInfo> getMessages() { return messages; }
    public void setMessages(List<UnreadMessageInfo> messages) { this.messages = messages; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UnreadMessageInfo {
        private Long id;
        private String senderName;
        private String subject;
        private String preview;
        private LocalDateTime sentAt;

        // Constructors
        public UnreadMessageInfo() {}

        public UnreadMessageInfo(Long id, String senderName, String subject) {
            this.id = id;
            this.senderName = senderName;
            this.subject = subject;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getSenderName() { return senderName; }
        public void setSenderName(String senderName) { this.senderName = senderName; }

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }

        public String getPreview() { return preview; }
        public void setPreview(String preview) { this.preview = preview; }

        public LocalDateTime getSentAt() { return sentAt; }
        public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    }
}