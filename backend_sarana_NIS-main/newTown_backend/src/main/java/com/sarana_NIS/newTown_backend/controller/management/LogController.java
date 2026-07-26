package com.sarana_NIS.newTown_backend.controller.management;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.ActivityLogResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.ActivityLog;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.management.ActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Activity Log Management", description = "Activity log viewing APIs")
public class LogController {

    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    private final ActivityLogService activityLogService;

    public LogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get all logs")
    public ResponseEntity<ApiResponse<PageResponse<ActivityLog>>> getAllLogs(Pageable pageable) {
        log.info("Fetching all logs");
        PageResponse<ActivityLog> response = activityLogService.getLatestLogs(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get log by ID")
    public ResponseEntity<ApiResponse<ActivityLog>> getLogById(@PathVariable Long id) {
        log.info("Fetching log: {}", id);
        ActivityLog logEntry = activityLogService.getLogById(id);
        return ResponseEntity.ok(ApiResponse.success(logEntry));
    }

    @GetMapping("/user/{userId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get logs by user")
    public ResponseEntity<ApiResponse<List<ActivityLog>>> getLogsByUser(@PathVariable Long userId) {
        log.info("Fetching logs by user: {}", userId);
        List<ActivityLog> logs = activityLogService.getLogsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/action/{action}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get logs by action")
    public ResponseEntity<ApiResponse<List<ActivityLog>>> getLogsByAction(@PathVariable String action) {
        log.info("Fetching logs by action: {}", action);
        List<ActivityLog> logs = activityLogService.getLogsByAction(action);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get logs by entity")
    public ResponseEntity<ApiResponse<List<ActivityLog>>> getLogsByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        log.info("Fetching logs by entity: {} - {}", entityType, entityId);
        List<ActivityLog> logs = activityLogService.getLogsByEntity(entityType, entityId);
        return ResponseEntity.ok(ApiResponse.success(logs));
    }

    @GetMapping("/date-range")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get logs by date range")
    public ResponseEntity<ApiResponse<PageResponse<ActivityLog>>> getLogsByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            Pageable pageable) {
        log.info("Fetching logs from {} to {}", start, end);
        PageResponse<ActivityLog> response = activityLogService.getLogsByDateRange(start, end, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/stats")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get log statistics")
    public ResponseEntity<ApiResponse<Object>> getLogStats(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        log.info("Fetching log statistics");
        // return activityLogService.getActivityStats(start, end);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/old")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Delete old logs")
    public ResponseEntity<ApiResponse<Void>> deleteOldLogs(@RequestParam LocalDateTime olderThan) {
        log.info("Deleting logs older than: {}", olderThan);
        activityLogService.deleteLogsOlderThan(olderThan);
        return ResponseEntity.ok(ApiResponse.success("Old logs deleted successfully", null));
    }

    @DeleteMapping("/{id}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Delete log")
    public ResponseEntity<ApiResponse<Void>> deleteLog(@PathVariable Long id) {
        log.info("Deleting log: {}", id);
        activityLogService.deleteLog(id);
        return ResponseEntity.ok(ApiResponse.success("Log deleted successfully", null));
    }
}