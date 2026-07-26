package com.sarana_NIS.newTown_backend.controller.management;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.ActivityLogResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.ActivityLog;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.management.ActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/management/activity-logs")
@Tag(name = "Activity Log Management", description = "Activity log viewing APIs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping("/{logId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get activity log by ID")
    public ResponseEntity<ApiResponse<ActivityLog>> getLogById(
            @PathVariable Long logId) {
        System.out.println("Fetching activity log: " + logId);
        ActivityLog log = activityLogService.getLogById(logId);
        return ResponseEntity.ok(ApiResponse.success(log));
    }

    @GetMapping("/user/{userId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get activity logs by user")
    public ResponseEntity<ApiResponse<List<ActivityLog>>> getLogsByUser(
            @PathVariable Long userId) {
        System.out.println("Fetching activity logs for user: " + userId);
        List<ActivityLog> logs = activityLogService.getLogsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/action/{action}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get activity logs by action")
    public ResponseEntity<ApiResponse<List<ActivityLog>>> getLogsByAction(
            @PathVariable String action) {
        System.out.println("Fetching activity logs by action: " + action);
        List<ActivityLog> logs = activityLogService.getLogsByAction(action);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get activity logs by entity")
    public ResponseEntity<ApiResponse<List<ActivityLog>>> getLogsByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        System.out.println("Fetching activity logs for entity: " + entityType + " - " + entityId);
        List<ActivityLog> logs = activityLogService.getLogsByEntity(entityType, entityId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/ip/{ipAddress}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get activity logs by IP address")
    public ResponseEntity<ApiResponse<List<ActivityLog>>> getLogsByIpAddress(
            @PathVariable String ipAddress) {
        System.out.println("Fetching activity logs by IP: " + ipAddress);
        List<ActivityLog> logs = activityLogService.getLogsByIpAddress(ipAddress);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/date-range")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get activity logs by date range")
    public ResponseEntity<ApiResponse<PageResponse<ActivityLog>>> getLogsByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            Pageable pageable) {
        System.out.println("Fetching activity logs from " + start + " to " + end);
        PageResponse<ActivityLog> response = activityLogService.getLogsByDateRange(start, end, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/latest")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get latest activity logs")
    public ResponseEntity<ApiResponse<PageResponse<ActivityLog>>> getLatestLogs(
            Pageable pageable) {
        System.out.println("Fetching latest activity logs");
        PageResponse<ActivityLog> response = activityLogService.getLatestLogs(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/stats")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get activity statistics")
    public ResponseEntity<ApiResponse<Object>> getActivityStats() {
        System.out.println("Fetching activity statistics");
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/old")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Delete old activity logs")
    public ResponseEntity<ApiResponse<Void>> deleteLogsOlderThan(
            @RequestParam LocalDateTime date) {
        System.out.println("Deleting activity logs older than: " + date);
        activityLogService.deleteLogsOlderThan(date);
        return ResponseEntity.ok(ApiResponse.success("Old logs deleted successfully", null));
    }
}