package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.dashboard.TeacherDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/teacher/dashboard")
@Tag(name = "Teacher Dashboard", description = "Teacher dashboard APIs")
public class TeacherDashboardController {

    private final TeacherDashboardService dashboardService;

    public TeacherDashboardController(TeacherDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @RequiresRole("TEACHER")
    @Operation(summary = "Get teacher dashboard statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        System.out.println("Fetching teacher dashboard");
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/classes")
    @RequiresRole("TEACHER")
    @Operation(summary = "Get teacher classes")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getClasses() {
        System.out.println("Fetching teacher classes");
        Map<String, Object> classes = dashboardService.getClasses();
        return ResponseEntity.ok(ApiResponse.success(classes));
    }

    @GetMapping("/tasks")
    @RequiresRole("TEACHER")
    @Operation(summary = "Get teacher tasks")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTasks() {
        System.out.println("Fetching teacher tasks");
        Map<String, Object> tasks = dashboardService.getTasks();
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }
}