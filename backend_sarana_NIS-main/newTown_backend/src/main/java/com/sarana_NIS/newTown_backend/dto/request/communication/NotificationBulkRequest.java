package com.sarana_NIS.newTown_backend.dto.request.communication;

import com.sarana_NIS.newTown_backend.model.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class NotificationBulkRequest {

    @NotEmpty(message = "At least one user ID is required")
    private List<Long> userIds = new ArrayList<>();

    @NotNull(message = "Notification type is required")
    private NotificationType type;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @NotBlank(message = "Message is required")
    @Size(max = 1000, message = "Message must be less than 1000 characters")
    private String message;

    @Size(max = 500, message = "Link must be less than 500 characters")
    private String link;

    // Constructors
    public NotificationBulkRequest() {}

    public NotificationBulkRequest(List<Long> userIds, NotificationType type, String title, String message) {
        this.userIds = userIds;
        this.type = type;
        this.title = title;
        this.message = message;
    }

    // Getters and Setters
    public List<Long> getUserIds() { return userIds; }
    public void setUserIds(List<Long> userIds) { this.userIds = userIds; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}