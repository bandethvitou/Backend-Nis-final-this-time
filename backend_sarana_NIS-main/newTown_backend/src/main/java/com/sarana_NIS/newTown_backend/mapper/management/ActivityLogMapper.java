package com.sarana_NIS.newTown_backend.mapper.management;

import com.sarana_NIS.newTown_backend.dto.response.management.ActivityLogResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.ManagementDashboardResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.ActivityLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ActivityLogMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "userName", source = "user.fullName")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "timestamp", source = "createdAt")
    ActivityLogResponse toResponse(ActivityLog activityLog);

    // ========== Dashboard Mapping ==========
    default ManagementDashboardResponse.RecentActivity toRecentActivity(ActivityLog log) {
        if (log == null) return null;

        ManagementDashboardResponse.RecentActivity activity =
                new ManagementDashboardResponse.RecentActivity();
        activity.setAction(log.getAction());
        activity.setDescription(log.getAction() + " on " + log.getEntityType());
        activity.setType(log.getEntityType());
        activity.setUser(log.getUser() != null ? log.getUser().getFullName() : "System");
        activity.setTimestamp(log.getCreatedAt() != null ? log.getCreatedAt() : LocalDateTime.now());

        return activity;
    }

    // ========== Batch Mapping ==========
    default List<ManagementDashboardResponse.RecentActivity> toRecentActivities(
            List<ActivityLog> logs) {
        if (logs == null) return new java.util.ArrayList<>();
        return logs.stream()
                .map(this::toRecentActivity)
                .collect(java.util.stream.Collectors.toList());
    }
}