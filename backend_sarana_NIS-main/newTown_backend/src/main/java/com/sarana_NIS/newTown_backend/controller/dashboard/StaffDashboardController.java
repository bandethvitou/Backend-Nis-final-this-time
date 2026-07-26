package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.dashboard.StaffDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/staff/dashboard")
@Tag(name = "Staff Dashboard", description = "Staff dashboard APIs")
public class StaffDashboardController {

    private static final Logger log = LoggerFactory.getLogger(StaffDashboardController.class);

    private final StaffDashboardService dashboardService;

    public StaffDashboardController(StaffDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @RequiresRole({"STAFF", "NON_OFFICE_STAFF"})
    @Operation(summary = "Get staff dashboard statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        log.info("Fetching staff dashboard");
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/tasks")
    @RequiresRole({"STAFF", "NON_OFFICE_STAFF"})
    @Operation(summary = "Get staff tasks")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTasks() {
        log.info("Fetching staff tasks");
        Map<String, Object> tasks = dashboardService.getTasks();
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    @GetMapping("/schedule")
    @RequiresRole({"STAFF", "NON_OFFICE_STAFF"})
    @Operation(summary = "Get staff schedule")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSchedule() {
        log.info("Fetching staff schedule");
        Map<String, Object> schedule = dashboardService.getSchedule();
        return ResponseEntity.ok(ApiResponse.success(schedule));
    }
}