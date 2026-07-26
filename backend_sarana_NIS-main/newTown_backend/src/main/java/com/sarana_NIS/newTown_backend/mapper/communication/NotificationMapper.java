package com.sarana_NIS.newTown_backend.mapper.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.NotificationRequest;
import com.sarana_NIS.newTown_backend.dto.response.communication.NotificationResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.NotificationSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NotificationMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "link", source = "link")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.fullName")
    @Mapping(target = "isUnread", expression = "java(!notification.getIsRead())")
    @Mapping(target = "sentAt", source = "sentAt")
    @Mapping(target = "readAt", source = "readAt")
    @Mapping(target = "expiresAt", source = "expiresAt")
    @Mapping(target = "isExpired", expression = "java(notification.isExpired())")
    @Mapping(target = "isHighPriority", expression = "java(notification.isHighPriority())")
    NotificationResponse toResponse(Notification notification);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isRead", constant = "false")
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "sentAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isArchived", constant = "false")
    @Mapping(target = "expiresAt", ignore = true)
    Notification toEntity(NotificationRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isRead", ignore = true)
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "isArchived", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    void updateEntity(NotificationRequest request, @MappingTarget Notification notification);

    // ========== Summary Response ==========
    default NotificationSummaryResponse toSummaryResponse(
            List<Notification> notifications,
            long totalUnread) {

        NotificationSummaryResponse response = new NotificationSummaryResponse();
        response.setTotalUnread(totalUnread);
        response.setTotalNotifications((long) notifications.size());

        // Recent notifications (last 10)
        response.setRecentNotifications(notifications.stream()
                .sorted((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()))
                .limit(10)
                .map(this::toResponse)
                .collect(Collectors.toList()));

        // Unread notifications
        response.setUnreadNotifications(notifications.stream()
                .filter(n -> !n.getIsRead())
                .sorted((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()))
                .map(this::toResponse)
                .collect(Collectors.toList()));

        // Count by type
        notifications.stream()
                .collect(Collectors.groupingBy(Notification::getType))
                .forEach((type, list) -> {
                    response.getCountByType().put(type.getDisplayName(), list.size());
                });

        return response;
    }
}