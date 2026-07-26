package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.dashboard.StudentDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/student/dashboard")
@Tag(name = "Student Dashboard", description = "Student dashboard APIs")
public class StudentDashboardController {

    private final StudentDashboardService dashboardService;

    public StudentDashboardController(StudentDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @RequiresRole("STUDENT")
    @Operation(summary = "Get student dashboard statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        System.out.println("Fetching student dashboard");
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/scores")
    @RequiresRole("STUDENT")
    @Operation(summary = "Get student scores")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getScores() {
        System.out.println("Fetching student scores");
        Map<String, Object> scores = dashboardService.getScores();
        return ResponseEntity.ok(ApiResponse.success(scores));
    }

    @GetMapping("/attendance")
    @RequiresRole("STUDENT")
    @Operation(summary = "Get student attendance")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAttendance() {
        System.out.println("Fetching student attendance");
        Map<String, Object> attendance = dashboardService.getAttendance();
        return ResponseEntity.ok(ApiResponse.success(attendance));
    }

    @GetMapping("/homework")
    @RequiresRole("STUDENT")
    @Operation(summary = "Get student homework")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHomework() {
        System.out.println("Fetching student homework");
        Map<String, Object> homework = dashboardService.getHomework();
        return ResponseEntity.ok(ApiResponse.success(homework));
    }
}