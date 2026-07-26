package com.sarana_NIS.newTown_backend.mapper.management;

import com.sarana_NIS.newTown_backend.dto.request.management.TaskAssignmentRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.TaskResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.TaskSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Task;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaskMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "dueDate", source = "dueDate")
    @Mapping(target = "assignedToId", source = "assignedTo.id")
    @Mapping(target = "assignedTo", source = "assignedTo.fullName")
    @Mapping(target = "assignedById", source = "assignedBy.id")
    @Mapping(target = "assignedBy", source = "assignedBy.fullName")
    @Mapping(target = "assignedDate", source = "assignedDate")
    @Mapping(target = "completedAt", source = "completedAt")
    @Mapping(target = "completionNotes", source = "completionNotes")
    @Mapping(target = "isRecurring", source = "isRecurring")
    @Mapping(target = "priorityColor", expression = "java(getPriorityColor(task.getPriority()))")
    @Mapping(target = "statusColor", expression = "java(getStatusColor(task.getStatus()))")
    @Mapping(target = "isOverdue", expression = "java(task.isOverdue())")
    @Mapping(target = "daysRemaining", expression = "java((int) task.calculateDaysRemaining())")
    TaskResponse toResponse(Task task);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "assignedBy", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "completionNotes", ignore = true)
    @Mapping(target = "assignedDate", expression = "java(java.time.LocalDateTime.now())")
    Task toEntity(TaskAssignmentRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "assignedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "completionNotes", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    void updateEntity(TaskAssignmentRequest request, @MappingTarget Task task);

    // ========== Summary Mapping ==========
    default TaskSummaryResponse toSummaryResponse(List<Task> tasks) {
        if (tasks == null) return new TaskSummaryResponse();

        TaskSummaryResponse summary = new TaskSummaryResponse();
        summary.setTotalTasks(tasks.size());

        int pending = 0, completed = 0, overdue = 0;
        int high = 0, medium = 0, low = 0;

        for (Task task : tasks) {
            // Status counts
            if (task.getStatus() != null) {
                switch (task.getStatus()) {
                    case PENDING:
                    case IN_PROGRESS:
                        pending++;
                        break;
                    case COMPLETED:
                        completed++;
                        break;
                    default:
                        break;
                }
            }

            // Overdue check
            if (task.isOverdue()) {
                overdue++;
            }

            // Priority counts
            if (task.getPriority() != null) {
                switch (task.getPriority()) {
                    case HIGH:
                        high++;
                        break;
                    case MEDIUM:
                        medium++;
                        break;
                    case LOW:
                        low++;
                        break;
                    default:
                        break;
                }
            }
        }

        summary.setPending(pending);
        summary.setCompleted(completed);
        summary.setOverdue(overdue);
        summary.setHighPriority(high);
        summary.setMediumPriority(medium);
        summary.setLowPriority(low);

        return summary;
    }

    // ========== Helper Methods ==========

    default String getPriorityColor(Priority priority) {
        if (priority == null) return "#6B7280";
        switch (priority) {
            case HIGH: return "#EF4444";
            case MEDIUM: return "#F59E0B";
            case LOW: return "#10B981";
            default: return "#6B7280";
        }
    }

    default String getStatusColor(TaskStatus status) {
        if (status == null) return "#6B7280";
        switch (status) {
            case PENDING: return "#F59E0B";
            case IN_PROGRESS: return "#3B82F6";
            case COMPLETED: return "#10B981";
            case CANCELLED: return "#EF4444";
            default: return "#6B7280";
        }
    }
}