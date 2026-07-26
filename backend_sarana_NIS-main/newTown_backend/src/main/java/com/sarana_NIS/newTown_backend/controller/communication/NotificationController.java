package com.sarana_NIS.newTown_backend.controller.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.NotificationBulkRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.NotificationSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Notification;
import com.sarana_NIS.newTown_backend.model.enums.NotificationType;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.communication.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/communication/notifications")
@Tag(name = "Notification Management", description = "Notification management APIs")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @RequiresPermission("NOTIFICATION_CREATE")
    @Operation(summary = "Create a notification for a user")
    public ResponseEntity<ApiResponse<Notification>> createNotification(
            @RequestParam Long userId,
            @RequestParam NotificationType type,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(required = false) String link) {
        System.out.println("Creating notification for user: " + userId);
        Notification notification = notificationService.createNotification(userId, type, title, message, link);
        return ResponseEntity.ok(ApiResponse.success("Notification created successfully", notification));
    }

    @PostMapping("/bulk")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @RequiresPermission("NOTIFICATION_CREATE")
    @Operation(summary = "Create bulk notifications")
    public ResponseEntity<ApiResponse<List<Notification>>> createBulkNotifications(
            @Valid @RequestBody NotificationBulkRequest request) {
        System.out.println("Creating bulk notifications for " + request.getUserIds().size() + " users");
        List<Notification> notifications = notificationService.createBulkNotifications(request);
        return ResponseEntity.ok(ApiResponse.success("Bulk notifications created successfully", notifications));
    }

    @GetMapping("/user/{userId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @Operation(summary = "Get notifications by user")
    public ResponseEntity<ApiResponse<Page<Notification>>> getNotificationsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        System.out.println("Fetching notifications for user: " + userId);
        Page<Notification> notifications = notificationService.getNotificationsByUser(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(notifications));
    }

    @GetMapping("/user/{userId}/unread")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @Operation(summary = "Get unread notifications by user")
    public ResponseEntity<ApiResponse<Page<Notification>>> getUnreadNotificationsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        System.out.println("Fetching unread notifications for user: " + userId);
        Page<Notification> notifications = notificationService.getUnreadNotificationsByUser(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(notifications));
    }

    @GetMapping("/user/{userId}/recent")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @Operation(summary = "Get recent notifications by user")
    public ResponseEntity<ApiResponse<List<Notification>>> getRecentNotificationsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        System.out.println("Fetching recent notifications for user: " + userId);
        List<Notification> notifications = notificationService.getRecentNotificationsByUser(userId, limit);
        return ResponseEntity.ok(ApiResponse.success(notifications));
    }

    @GetMapping("/user/{userId}/type/{type}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @Operation(summary = "Get notifications by type")
    public ResponseEntity<ApiResponse<List<Notification>>> getNotificationsByType(
            @PathVariable Long userId,
            @PathVariable NotificationType type) {
        System.out.println("Fetching notifications by type: " + type + " for user: " + userId);
        List<Notification> notifications = notificationService.getNotificationsByType(userId, type);
        return ResponseEntity.ok(ApiResponse.success(notifications));
    }

    @GetMapping("/user/{userId}/unread/count")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @Operation(summary = "Count unread notifications")
    public ResponseEntity<ApiResponse<Long>> countUnreadNotifications(
            @PathVariable Long userId) {
        System.out.println("Counting unread notifications for user: " + userId);
        long count = notificationService.countUnreadNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @PostMapping("/{notificationId}/read")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @RequiresPermission("NOTIFICATION_UPDATE")
    @Operation(summary = "Mark notification as read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable Long notificationId) {
        System.out.println("Marking notification as read: " + notificationId);
        Long currentUserId = 1L;
        notificationService.markAsRead(notificationId, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read", null));
    }

    @PostMapping("/read-all")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @RequiresPermission("NOTIFICATION_UPDATE")
    @Operation(summary = "Mark all notifications as read")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead() {
        System.out.println("Marking all notifications as read");
        Long currentUserId = 1L;
        notificationService.markAllAsRead(currentUserId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", null));
    }

    @DeleteMapping("/{notificationId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @RequiresPermission("NOTIFICATION_DELETE")
    @Operation(summary = "Delete notification")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @PathVariable Long notificationId) {
        System.out.println("Deleting notification: " + notificationId);
        Long currentUserId = 1L;
        notificationService.deleteNotification(notificationId, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted successfully", null));
    }

    @DeleteMapping("/old")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "HR_ADMIN"})
    @RequiresPermission("NOTIFICATION_DELETE")
    @Operation(summary = "Delete old notifications")
    public ResponseEntity<ApiResponse<Void>> deleteOldNotifications(
            @RequestParam LocalDateTime olderThan) {
        System.out.println("Deleting notifications older than: " + olderThan);
        Long currentUserId = 1L;
        notificationService.deleteOldNotifications(currentUserId, olderThan);
        return ResponseEntity.ok(ApiResponse.success("Old notifications deleted successfully", null));
    }
}