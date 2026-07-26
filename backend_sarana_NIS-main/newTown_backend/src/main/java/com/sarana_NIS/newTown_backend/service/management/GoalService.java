package com.sarana_NIS.newTown_backend.service.management;

import com.sarana_NIS.newTown_backend.dto.request.management.GoalProgressRequest;
import com.sarana_NIS.newTown_backend.dto.response.management.GoalDashboardResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.GoalProgressResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.Goal;
import com.sarana_NIS.newTown_backend.model.enums.GoalStatus;
import com.sarana_NIS.newTown_backend.repository.GoalRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class GoalService {

    private static final Logger log = LoggerFactory.getLogger(GoalService.class);

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalService(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Goal createGoal(Goal goal, Long managerId) {
        if (goal.getDeadline() == null) {
            throw new RuntimeException("Goal deadline is required");
        }

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        goal.setManager(manager);
        goal.setStatus(GoalStatus.NOT_STARTED);
        goal.setCurrent(BigDecimal.ZERO);

        Goal saved = goalRepository.save(goal);
        log.info("Goal created: {}", saved.getTitle());
        return saved;
    }

    @Transactional
    public Goal updateProgress(GoalProgressRequest request) {
        Goal goal = goalRepository.findById(request.getGoalId())
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        goal.setCurrent(request.getCurrent());

        if (goal.getCurrent().compareTo(goal.getTarget()) >= 0) {
            goal.setStatus(GoalStatus.COMPLETED);
        } else if (goal.getStatus() == GoalStatus.NOT_STARTED &&
                goal.getCurrent().compareTo(BigDecimal.ZERO) > 0) {
            goal.setStatus(GoalStatus.IN_PROGRESS);
        }

        if (goal.getStatus() != GoalStatus.COMPLETED &&
                LocalDate.now().isAfter(goal.getDeadline())) {
            goal.setStatus(GoalStatus.OVERDUE);
        }

        Goal saved = goalRepository.save(goal);
        log.info("Goal progress updated: {} -> {}", request.getGoalId(), request.getCurrent());
        return saved;
    }

    public Goal getGoalById(Long goalId) {
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
    }

    public List<Goal> getGoalsByManager(Long managerId) {
        return goalRepository.findByManagerId(managerId);
    }

    public List<Goal> getGoalsByStatus(GoalStatus status) {
        return goalRepository.findByStatus(status);
    }

    public List<Goal> getOverdueGoals() {
        return goalRepository.findOverdueGoals(LocalDate.now());
    }

    public List<Goal> getActiveGoalsByManager(Long managerId) {
        return goalRepository.findActiveByManager(managerId);
    }

    public List<Goal> getCompletedGoals() {
        return goalRepository.findCompletedGoals();
    }

    public List<Goal> getIncompleteGoals() {
        return goalRepository.findIncompleteGoals();
    }

    public PageResponse<Goal> getAllGoals(Pageable pageable) {
        Page<Goal> page = goalRepository.findAll(pageable);
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    public GoalProgressResponse getGoalProgress(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        GoalProgressResponse response = new GoalProgressResponse();
        response.setGoalId(goal.getId());
        response.setTitle(goal.getTitle());
        response.setTarget(goal.getTarget());
        response.setCurrent(goal.getCurrent());

        if (goal.getTarget() != null && goal.getTarget().compareTo(BigDecimal.ZERO) > 0) {
            response.setPercentage(goal.getCurrent().divide(goal.getTarget(), 2, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100)).intValue());
        }

        response.setStatus(goal.getStatus());
        response.setDeadline(goal.getDeadline());

        String trend = "STABLE";
        if (goal.getCurrent() != null && goal.getCurrent().compareTo(BigDecimal.ZERO) > 0) {
            if (goal.getCurrent().compareTo(goal.getTarget().multiply(BigDecimal.valueOf(0.5))) >= 0) {
                trend = "UP";
            } else {
                trend = "DOWN";
            }
        }
        response.setTrend(trend);

        return response;
    }

    public GoalDashboardResponse getGoalDashboard() {
        GoalDashboardResponse dashboard = new GoalDashboardResponse();

        dashboard.setTotalGoals((int) goalRepository.count());

        dashboard.setNotStarted(goalRepository.countByStatus(GoalStatus.NOT_STARTED).intValue());
        dashboard.setInProgress(goalRepository.countByStatus(GoalStatus.IN_PROGRESS).intValue());
        dashboard.setCompleted(goalRepository.countByStatus(GoalStatus.COMPLETED).intValue());
        dashboard.setOverdue(goalRepository.countByStatus(GoalStatus.OVERDUE).intValue());

        List<Goal> allGoals = goalRepository.findAll();
        if (!allGoals.isEmpty()) {
            double totalProgress = allGoals.stream()
                    .mapToDouble(g -> {
                        if (g.getTarget() != null && g.getTarget().compareTo(BigDecimal.ZERO) > 0) {
                            return g.getCurrent().doubleValue() / g.getTarget().doubleValue() * 100;
                        }
                        return g.getStatus() == GoalStatus.COMPLETED ? 100 : 0;
                    })
                    .average()
                    .orElse(0);
            dashboard.setOverallProgress(totalProgress);
        }

        return dashboard;
    }

    @Transactional
    public void deleteGoal(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        goalRepository.delete(goal);
        log.info("Goal deleted: {}", goalId);
    }
}