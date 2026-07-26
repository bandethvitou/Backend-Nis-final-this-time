package com.sarana_NIS.newTown_backend.controller.management;

import com.sarana_NIS.newTown_backend.dto.request.management.TaskAssignmentRequest;
import com.sarana_NIS.newTown_backend.dto.request.management.TaskStatusRequest;
import com.sarana_NIS.newTown_backend.dto.request.management.BulkTaskRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.TaskResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.TaskSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Task;
import com.sarana_NIS.newTown_backend.model.enums.TaskStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.management.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "Task management APIs")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ============================================================
    // CREATE
    // ============================================================

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @RequiresPermission("TASK_CREATE")
    @Operation(summary = "Create a new task")
    public ResponseEntity<ApiResponse<Task>> createTask(
            @Valid @RequestBody TaskAssignmentRequest request) {
        System.out.println("Creating task: " + request.getTitle());
        Long currentUserId = 1L; // TODO: Get from SecurityContext
        Task task = taskService.createTask(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Task created successfully", task));
    }

    @PostMapping("/bulk")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @RequiresPermission("TASK_CREATE")
    @Operation(summary = "Create bulk tasks")
    public ResponseEntity<ApiResponse<List<Task>>> createBulkTasks(
            @Valid @RequestBody TaskAssignmentRequest request) {
        Long currentUserId = 1L; // TODO: Get from SecurityContext
        System.out.println("Creating bulk tasks");
        List<Task> tasks = taskService.createBulkTasks(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Bulk tasks created successfully", tasks));
    }

    // ============================================================
    // READ - ALL
    // ============================================================

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get all tasks with pagination")
    public ResponseEntity<ApiResponse<PageResponse<Task>>> getAllTasks(
            Pageable pageable) {
        System.out.println("Fetching all tasks");
        PageResponse<Task> response = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ============================================================
    // READ - BY ID
    // ============================================================

    @GetMapping("/{taskId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get task by ID")
    public ResponseEntity<ApiResponse<Task>> getTaskById(
            @PathVariable Long taskId) {
        System.out.println("Fetching task: " + taskId);
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(ApiResponse.success(task));
    }

    // ============================================================
    // READ - BY ASSIGNEE
    // ============================================================

    @GetMapping("/assignee/{userId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get tasks by assignee")
    public ResponseEntity<ApiResponse<List<Task>>> getTasksByAssignee(
            @PathVariable Long userId) {
        System.out.println("Fetching tasks by assignee: " + userId);
        List<Task> tasks = taskService.getTasksByAssignee(userId);
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    // ============================================================
    // READ - BY STATUS
    // ============================================================

    @GetMapping("/status/{status}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get tasks by status")
    public ResponseEntity<ApiResponse<List<Task>>> getTasksByStatus(
            @PathVariable TaskStatus status) {
        System.out.println("Fetching tasks by status: " + status);
        List<Task> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    // ============================================================
    // READ - PENDING BY ASSIGNEE
    // ============================================================

    @GetMapping("/pending/{userId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get pending tasks by assignee")
    public ResponseEntity<ApiResponse<List<Task>>> getPendingTasksByAssignee(
            @PathVariable Long userId) {
        System.out.println("Fetching pending tasks by assignee: " + userId);
        List<Task> tasks = taskService.getPendingTasksByAssignee(userId);
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    // ============================================================
    // READ - OVERDUE BY ASSIGNEE
    // ============================================================

    @GetMapping("/overdue/{userId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get overdue tasks by assignee")
    public ResponseEntity<ApiResponse<List<Task>>> getOverdueTasksByAssignee(
            @PathVariable Long userId) {
        System.out.println("Fetching overdue tasks by assignee: " + userId);
        List<Task> tasks = taskService.getOverdueTasksByAssignee(userId);
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    // ============================================================
    // READ - BY DATE RANGE
    // ============================================================

    @GetMapping("/date-range")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get tasks by date range")
    public ResponseEntity<ApiResponse<List<Task>>> getTasksByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        System.out.println("Fetching tasks from " + startDate + " to " + endDate);
        List<Task> tasks = taskService.getTasksByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    // ============================================================
    // READ - UPCOMING
    // ============================================================

    @GetMapping("/upcoming")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get upcoming tasks")
    public ResponseEntity<ApiResponse<List<Task>>> getUpcomingTasks() {
        System.out.println("Fetching upcoming tasks");
        List<Task> tasks = taskService.getUpcomingTasks();
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    // ============================================================
    // READ - SUMMARY
    // ============================================================

    @GetMapping("/summary")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get task summary")
    public ResponseEntity<ApiResponse<TaskSummaryResponse>> getTaskSummary() {
        System.out.println("Fetching task summary");
        TaskSummaryResponse summary = taskService.getTaskSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    // ============================================================
    // UPDATE - STATUS
    // ============================================================

    @PatchMapping("/{taskId}/status")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TASK_UPDATE")
    @Operation(summary = "Update task status")
    public ResponseEntity<ApiResponse<Task>> updateTaskStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskStatusRequest request) {
        System.out.println("Updating task status: " + taskId);
        // Ensure the request has the correct task ID
        request.setTaskId(taskId);
        Task task = taskService.updateTaskStatus(request);
        return ResponseEntity.ok(ApiResponse.success("Task status updated successfully", task));
    }

    // ============================================================
    // UPDATE - COMPLETE
    // ============================================================

    @PostMapping("/{taskId}/complete")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TASK_COMPLETE")
    @Operation(summary = "Mark task as complete")
    public ResponseEntity<ApiResponse<Task>> markTaskComplete(
            @PathVariable Long taskId) {
        System.out.println("Marking task as complete: " + taskId);
        Task task = taskService.markTaskComplete(taskId);
        return ResponseEntity.ok(ApiResponse.success("Task marked as complete", task));
    }

    // ============================================================
    // BULK ACTION
    // ============================================================

    @PostMapping("/bulk-action")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @RequiresPermission("TASK_UPDATE")
    @Operation(summary = "Bulk action on tasks")
    public ResponseEntity<ApiResponse<Void>> bulkAction(
            @Valid @RequestBody BulkTaskRequest request) {
        System.out.println("Bulk action: " + request.getAction() + " on " + request.getTaskIds().size() + " tasks");
        taskService.bulkAction(request);
        return ResponseEntity.ok(ApiResponse.success("Bulk action completed successfully", null));
    }

    // ============================================================
    // DELETE
    // ============================================================

    @DeleteMapping("/{taskId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @RequiresPermission("TASK_DELETE")
    @Operation(summary = "Delete task")
    public ResponseEntity<ApiResponse<Void>> deleteTask(
            @PathVariable Long taskId) {
        System.out.println("Deleting task: " + taskId);
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(ApiResponse.success("Task deleted successfully", null));
    }
}