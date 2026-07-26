package com.sarana_NIS.newTown_backend.controller.management;

import com.sarana_NIS.newTown_backend.dto.request.management.GoalProgressRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.GoalProgressResponse;
import com.sarana_NIS.newTown_backend.dto.response.management.GoalDashboardResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Goal;
import com.sarana_NIS.newTown_backend.model.enums.GoalStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.management.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@Tag(name = "Goal Management", description = "Goal management APIs")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("GOAL_CREATE")
    @Operation(summary = "Create a new goal")
    public ResponseEntity<ApiResponse<Goal>> createGoal(
            @Valid @RequestBody Goal goal) {
        System.out.println("Creating goal: " + goal.getTitle());
        Long currentUserId = 1L;
        Goal created = goalService.createGoal(goal, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Goal created successfully", created));
    }

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get all goals with pagination")
    public ResponseEntity<ApiResponse<PageResponse<Goal>>> getAllGoals(
            Pageable pageable) {
        System.out.println("Fetching all goals");
        PageResponse<Goal> response = goalService.getAllGoals(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{goalId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get goal by ID")
    public ResponseEntity<ApiResponse<Goal>> getGoalById(
            @PathVariable Long goalId) {
        System.out.println("Fetching goal: " + goalId);
        Goal goal = goalService.getGoalById(goalId);
        return ResponseEntity.ok(ApiResponse.success(goal));
    }

    @GetMapping("/manager/{managerId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get goals by manager")
    public ResponseEntity<ApiResponse<List<Goal>>> getGoalsByManager(
            @PathVariable Long managerId) {
        System.out.println("Fetching goals by manager: " + managerId);
        List<Goal> goals = goalService.getGoalsByManager(managerId);
        return ResponseEntity.ok(ApiResponse.success(goals));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get goals by status")
    public ResponseEntity<ApiResponse<List<Goal>>> getGoalsByStatus(
            @PathVariable GoalStatus status) {
        System.out.println("Fetching goals by status: " + status);
        List<Goal> goals = goalService.getGoalsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(goals));
    }

    @GetMapping("/overdue")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get overdue goals")
    public ResponseEntity<ApiResponse<List<Goal>>> getOverdueGoals() {
        System.out.println("Fetching overdue goals");
        List<Goal> goals = goalService.getOverdueGoals();
        return ResponseEntity.ok(ApiResponse.success(goals));
    }

    @GetMapping("/active/manager/{managerId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get active goals by manager")
    public ResponseEntity<ApiResponse<List<Goal>>> getActiveGoalsByManager(
            @PathVariable Long managerId) {
        System.out.println("Fetching active goals by manager: " + managerId);
        List<Goal> goals = goalService.getActiveGoalsByManager(managerId);
        return ResponseEntity.ok(ApiResponse.success(goals));
    }

    @GetMapping("/dashboard")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @Operation(summary = "Get goal dashboard")
    public ResponseEntity<ApiResponse<GoalDashboardResponse>> getGoalDashboard() {
        System.out.println("Fetching goal dashboard");
        GoalDashboardResponse response = goalService.getGoalDashboard();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{goalId}/progress")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get goal progress")
    public ResponseEntity<ApiResponse<GoalProgressResponse>> getGoalProgress(
            @PathVariable Long goalId) {
        System.out.println("Fetching goal progress: " + goalId);
        GoalProgressResponse response = goalService.getGoalProgress(goalId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{goalId}/progress")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("GOAL_UPDATE")
    @Operation(summary = "Update goal progress")
    public ResponseEntity<ApiResponse<Goal>> updateProgress(
            @PathVariable Long goalId,
            @Valid @RequestBody GoalProgressRequest request) {
        System.out.println("Updating progress for goal: " + goalId);
        Goal goal = goalService.updateProgress(request);
        return ResponseEntity.ok(ApiResponse.success("Goal progress updated successfully", goal));
    }

    @DeleteMapping("/{goalId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("GOAL_DELETE")
    @Operation(summary = "Delete goal")
    public ResponseEntity<ApiResponse<Void>> deleteGoal(
            @PathVariable Long goalId) {
        System.out.println("Deleting goal: " + goalId);
        goalService.deleteGoal(goalId);
        return ResponseEntity.ok(ApiResponse.success("Goal deleted successfully", null));
    }
}