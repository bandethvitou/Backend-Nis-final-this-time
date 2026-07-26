package com.sarana_NIS.newTown_backend.service.management;

import com.sarana_NIS.newTown_backend.dto.request.management.TaskAssignmentRequest;
import com.sarana_NIS.newTown_backend.dto.request.management.TaskStatusRequest;
import com.sarana_NIS.newTown_backend.dto.request.management.BulkTaskRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.TaskSummaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.Task;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.TaskStatus;
import com.sarana_NIS.newTown_backend.repository.TaskRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // CREATE
    // ============================================================

    @Transactional
    public Task createTask(TaskAssignmentRequest request, Long assignedByUserId) {
        User assignedBy = userRepository.findById(assignedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        // ✅ Fix: request.getPriority() is already Priority enum
        task.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM);
        task.setStatus(TaskStatus.PENDING);
        task.setAssignedBy(assignedBy);
        task.setAssignedDate(LocalDateTime.now());

        if (request.getAssignedToId() != null) {
            User assignee = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignedTo(assignee);
        }

        Task saved = taskRepository.save(task);
        log.info("Task created: {}", saved.getTitle());
        return saved;
    }

    @Transactional
    public List<Task> createBulkTasks(TaskAssignmentRequest request, Long assignedByUserId) {
        User assignedBy = userRepository.findById(assignedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = new ArrayList<>();

        // Get list of assignee IDs
        List<Long> assigneeIds = request.getAssignedToUserIds();
        if (assigneeIds == null || assigneeIds.isEmpty()) {
            // If no list, use single assignee
            if (request.getAssignedToId() != null) {
                // ✅ Fix: Use Collections.singletonList() instead of List.of()
                assigneeIds = Collections.singletonList(request.getAssignedToId());
            } else {
                throw new RuntimeException("No assignee specified");
            }
        }

        for (Long assigneeId : assigneeIds) {
            User assignee = userRepository.findById(assigneeId)
                    .orElseThrow(() -> new RuntimeException("Assignee not found: " + assigneeId));

            Task task = new Task();
            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            task.setDueDate(request.getDueDate());
            // ✅ Fix: request.getPriority() is already Priority enum
            task.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM);
            task.setStatus(TaskStatus.PENDING);
            task.setAssignedBy(assignedBy);
            task.setAssignedTo(assignee);
            task.setAssignedDate(LocalDateTime.now());
            tasks.add(task);
        }

        List<Task> saved = taskRepository.saveAll(tasks);
        log.info("Bulk tasks created: {} tasks", saved.size());
        return saved;
    }

    // ============================================================
    // UPDATE STATUS
    // ============================================================

    @Transactional
    public Task updateTaskStatus(TaskStatusRequest request) {
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // ✅ Fix: request.getStatus() is String, use valueOf
        TaskStatus newStatus = TaskStatus.valueOf(request.getStatus());
        task.setStatus(newStatus);

        if (newStatus == TaskStatus.COMPLETED) {
            task.setCompletedAt(LocalDateTime.now());
        }

        Task saved = taskRepository.save(task);
        log.info("Task status updated: {} -> {}", request.getTaskId(), newStatus);
        return saved;
    }

    @Transactional
    public Task markTaskComplete(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletedAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);
        log.info("Task marked complete: {}", taskId);
        return saved;
    }

    // ============================================================
    // BULK ACTIONS
    // ============================================================

    @Transactional
    public void bulkAction(BulkTaskRequest request) {
        List<Task> tasks = taskRepository.findAllById(request.getTaskIds());

        switch (request.getAction()) {
            case "ASSIGN":
                if (request.getAssignToUserId() != null) {
                    User assignee = userRepository.findById(request.getAssignToUserId())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    for (Task task : tasks) {
                        task.setAssignedTo(assignee);
                    }
                }
                break;
            case "COMPLETE":
                for (Task task : tasks) {
                    task.setStatus(TaskStatus.COMPLETED);
                    task.setCompletedAt(LocalDateTime.now());
                }
                break;
            case "DELETE":
                taskRepository.deleteAll(tasks);
                return;
            case "UPDATE_PRIORITY":
                if (request.getPriority() != null) {
                    // ✅ Fix: request.getPriority() is String, use valueOf
                    Priority priority = Priority.valueOf(request.getPriority());
                    for (Task task : tasks) {
                        task.setPriority(priority);
                    }
                }
                break;
            default:
                throw new RuntimeException("Invalid bulk action: " + request.getAction());
        }

        if (!request.getAction().equals("DELETE")) {
            taskRepository.saveAll(tasks);
        }
        log.info("Bulk action '{}' completed on {} tasks", request.getAction(), tasks.size());
    }

    // ============================================================
    // READ - SINGLE
    // ============================================================

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // ============================================================
    // READ - BY ASSIGNEE
    // ============================================================

    public List<Task> getTasksByAssignee(Long userId) {
        return taskRepository.findByAssignedTo(userId);
    }

    public List<Task> getTasksByAssignedBy(Long userId) {
        return taskRepository.findByAssignedBy(userId);
    }

    public List<Task> getPendingTasksByAssignee(Long userId) {
        return taskRepository.findPendingByAssignee(userId);
    }

    public List<Task> getInProgressTasksByAssignee(Long userId) {
        return taskRepository.findInProgressByAssignee(userId);
    }

    public List<Task> getOverdueTasksByAssignee(Long userId) {
        return taskRepository.findOverdueByAssignee(userId, LocalDate.now());
    }

    // ============================================================
    // READ - BY STATUS
    // ============================================================

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    // ============================================================
    // READ - BY DATE RANGE
    // ============================================================

    public List<Task> getTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByDueDateBetween(startDate, endDate);
    }

    public List<Task> getUpcomingTasks() {
        return taskRepository.findUpcomingTasks(LocalDate.now());
    }

    // ============================================================
    // READ - PAGINATED
    // ============================================================

    public PageResponse<Task> getAllTasks(Pageable pageable) {
        Page<Task> page = taskRepository.findAll(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<Task> getTasksByAssigneeId(Long userId, Pageable pageable) {
        Page<Task> page = taskRepository.findByAssigneeId(userId, pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    // ============================================================
    // SUMMARY
    // ============================================================

    public TaskSummaryResponse getTaskSummary() {
        TaskSummaryResponse summary = new TaskSummaryResponse();

        List<Task> allTasks = taskRepository.findAll();
        summary.setTotalTasks(allTasks.size());

        List<Task> pendingTasks = taskRepository.findByStatus(TaskStatus.PENDING);
        List<Task> inProgressTasks = taskRepository.findByStatus(TaskStatus.IN_PROGRESS);
        int activeCount = pendingTasks.size() + inProgressTasks.size();
        summary.setPending(activeCount);

        List<Task> completedTasks = taskRepository.findByStatus(TaskStatus.COMPLETED);
        summary.setCompleted(completedTasks.size());

        // ✅ Fix: findOverdueTasks() should exist in TaskRepository
        List<Task> overdueTasks = taskRepository.findOverdueTasks(LocalDate.now());
        summary.setOverdue(overdueTasks.size());

        List<Task> highPriority = taskRepository.findByPriority(Priority.HIGH);
        List<Task> mediumPriority = taskRepository.findByPriority(Priority.MEDIUM);
        List<Task> lowPriority = taskRepository.findByPriority(Priority.LOW);

        summary.setHighPriority(highPriority.size());
        summary.setMediumPriority(mediumPriority.size());
        summary.setLowPriority(lowPriority.size());

        return summary;
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Transactional
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
        log.info("Task deleted: {}", taskId);
    }

    @Transactional
    public void deleteTasksByAssignee(Long userId) {
        List<Task> tasks = taskRepository.findByAssignedTo(userId);
        taskRepository.deleteAll(tasks);
        log.info("All tasks deleted for assignee: {}", userId);
    }
}