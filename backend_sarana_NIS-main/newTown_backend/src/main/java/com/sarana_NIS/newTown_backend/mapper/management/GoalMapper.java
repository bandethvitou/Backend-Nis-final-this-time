package com.sarana_NIS.newTown_backend.mapper.management;

import com.sarana_NIS.newTown_backend.dto.request.management.GoalRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.GoalDashboardResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.GoalProgressResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.GoalResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Goal;
import com.sarana_NIS.newTown_backend.model.enums.GoalStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GoalMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")  // ✅ Explicitly map id
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "managerName", source = "manager.fullName")
    @Mapping(target = "manager", source = "manager.fullName")
    @Mapping(target = "progressPercentage", expression = "java(calculateProgress(goal))")
    @Mapping(target = "statusColor", expression = "java(getStatusColor(goal.getStatus()))")
    @Mapping(target = "isOverdue", expression = "java(isGoalOverdue(goal))")
    @Mapping(target = "isCompleted", expression = "java(goal.getStatus() == GoalStatus.COMPLETED)")
    @Mapping(target = "daysRemaining", expression = "java(calculateDaysRemaining(goal))")
    GoalResponse toResponse(Goal goal);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "current", constant = "0")
    @Mapping(target = "status", constant = "NOT_STARTED")
    Goal toEntity(GoalRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "current", ignore = true)
    void updateEntity(GoalRequest request, @MappingTarget Goal goal);

    // ========== Progress Response ==========
    default GoalProgressResponse toProgressResponse(Goal goal) {
        if (goal == null) return null;

        GoalProgressResponse response = new GoalProgressResponse();
        response.setGoalId(goal.getId());  // ✅ Now works with BaseEntity id
        response.setTitle(goal.getTitle());
        response.setTarget(goal.getTarget());
        response.setCurrent(goal.getCurrent());
        response.setStatus(goal.getStatus());
        response.setDeadline(goal.getDeadline());

        // Calculate percentage
        if (goal.getTarget() != null && goal.getTarget().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal current = goal.getCurrent() != null ? goal.getCurrent() : BigDecimal.ZERO;
            BigDecimal percentage = current.divide(goal.getTarget(), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            response.setPercentage(percentage.intValue());
        } else {
            response.setPercentage(0);
        }

        // Determine trend
        response.setTrend(determineTrend(goal));

        return response;
    }

    // ========== Dashboard Response ==========
    default GoalDashboardResponse toDashboardResponse(List<Goal> goals) {
        if (goals == null) return new GoalDashboardResponse();

        GoalDashboardResponse dashboard = new GoalDashboardResponse();
        dashboard.setTotalGoals(goals.size());

        int notStarted = 0, inProgress = 0, completed = 0, overdue = 0;
        double totalProgress = 0;

        for (Goal goal : goals) {
            if (goal.getStatus() != null) {
                switch (goal.getStatus()) {
                    case NOT_STARTED:
                        notStarted++;
                        break;
                    case IN_PROGRESS:
                        inProgress++;
                        break;
                    case COMPLETED:
                        completed++;
                        break;
                    default:
                        break;
                }
            }

            if (isGoalOverdue(goal) && goal.getStatus() != GoalStatus.COMPLETED) {
                overdue++;
            }

            totalProgress += calculateProgress(goal);
        }

        dashboard.setNotStarted(notStarted);
        dashboard.setInProgress(inProgress);
        dashboard.setCompleted(completed);
        dashboard.setOverdue(overdue);
        dashboard.setOverallProgress(goals.isEmpty() ? 0.0 : totalProgress / goals.size());

        // Add recent goals (last 5)
        goals.stream()
                .sorted((g1, g2) -> {
                    if (g1.getCreatedAt() == null && g2.getCreatedAt() == null) return 0;
                    if (g1.getCreatedAt() == null) return 1;
                    if (g2.getCreatedAt() == null) return -1;
                    return g2.getCreatedAt().compareTo(g1.getCreatedAt());
                })
                .limit(5)
                .forEach(g -> dashboard.getRecentGoals().add(toResponse(g)));

        return dashboard;
    }

    // ========== Helper Methods ==========

    default Integer calculateProgress(Goal goal) {
        if (goal == null || goal.getTarget() == null || goal.getTarget().compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }
        BigDecimal current = goal.getCurrent() != null ? goal.getCurrent() : BigDecimal.ZERO;
        BigDecimal percentage = current.divide(goal.getTarget(), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return Math.min(percentage.intValue(), 100);
    }

    default String getStatusColor(GoalStatus status) {
        if (status == null) return "#6B7280";
        switch (status) {
            case NOT_STARTED: return "#6B7280";
            case IN_PROGRESS: return "#3B82F6";
            case COMPLETED: return "#10B981";
            case OVERDUE: return "#EF4444";
            default: return "#6B7280";
        }
    }

    default boolean isGoalOverdue(Goal goal) {
        if (goal == null || goal.getDeadline() == null) return false;
        if (goal.getStatus() == GoalStatus.COMPLETED) return false;
        return goal.getDeadline().isBefore(LocalDate.now());
    }

    default Integer calculateDaysRemaining(Goal goal) {
        if (goal == null || goal.getDeadline() == null) return 0;
        if (goal.getStatus() == GoalStatus.COMPLETED) return 0;
        return (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), goal.getDeadline());
    }

    default String determineTrend(Goal goal) {
        if (goal == null) return "STABLE";
        if (goal.getStatus() == GoalStatus.COMPLETED) return "COMPLETED";
        if (goal.getStatus() == GoalStatus.OVERDUE) return "OVERDUE";

        Integer progress = calculateProgress(goal);
        if (progress >= 75) return "ON_TRACK";
        if (progress >= 50) return "PROGRESSING";
        if (progress >= 25) return "SLOW";
        return "STALLED";
    }
}