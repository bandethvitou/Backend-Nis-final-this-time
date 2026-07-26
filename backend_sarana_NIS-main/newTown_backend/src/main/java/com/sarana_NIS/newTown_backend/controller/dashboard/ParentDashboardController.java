package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.dashboard.ParentDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/parent/dashboard")
@Tag(name = "Parent Dashboard", description = "Parent dashboard APIs")
public class ParentDashboardController {

    private final ParentDashboardService dashboardService;

    public ParentDashboardController(ParentDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @RequiresRole("PARENT")
    @Operation(summary = "Get parent dashboard statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        System.out.println("Fetching parent dashboard");
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/children")
    @RequiresRole("PARENT")
    @Operation(summary = "Get parent's children")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getChildren() {
        System.out.println("Fetching parent's children");
        Map<String, Object> children = dashboardService.getChildren();
        return ResponseEntity.ok(ApiResponse.success(children));
    }

    @GetMapping("/fees")
    @RequiresRole("PARENT")
    @Operation(summary = "Get fee status")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getFees() {
        System.out.println("Fetching fee status");
        Map<String, Object> fees = dashboardService.getFees();
        return ResponseEntity.ok(ApiResponse.success(fees));
    }
}