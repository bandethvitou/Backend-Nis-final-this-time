package com.sarana_NIS.newTown_backend.dto.response.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationSummaryResponse {
    private Long totalNotifications;
    private Long totalUnread;
    private List<NotificationResponse> recentNotifications;
    private List<NotificationResponse> unreadNotifications;
    private Map<String, Integer> countByType;

    // Constructors
    public NotificationSummaryResponse() {
        this.totalNotifications = 0L;
        this.totalUnread = 0L;
        this.recentNotifications = new ArrayList<>();
        this.unreadNotifications = new ArrayList<>();
        this.countByType = new HashMap<>();
    }

    // Getters and Setters
    public Long getTotalNotifications() {
        return totalNotifications;
    }

    public void setTotalNotifications(Long totalNotifications) {
        this.totalNotifications = totalNotifications;
    }

    public Long getTotalUnread() {
        return totalUnread;
    }

    public void setTotalUnread(Long totalUnread) {
        this.totalUnread = totalUnread;
    }

    public List<NotificationResponse> getRecentNotifications() {
        return recentNotifications;
    }

    public void setRecentNotifications(List<NotificationResponse> recentNotifications) {
        this.recentNotifications = recentNotifications;
    }

    public List<NotificationResponse> getUnreadNotifications() {
        return unreadNotifications;
    }

    public void setUnreadNotifications(List<NotificationResponse> unreadNotifications) {
        this.unreadNotifications = unreadNotifications;
    }

    public Map<String, Integer> getCountByType() {
        return countByType;
    }

    public void setCountByType(Map<String, Integer> countByType) {
        this.countByType = countByType;
    }
}