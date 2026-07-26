package com.sarana_NIS.newTown_backend.controller.dashboard;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.StaffResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.user.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/team")
@Tag(name = "Manager Team Management", description = "Manager team management APIs")
public class ManagerTeamController {

    private static final Logger log = LoggerFactory.getLogger(ManagerTeamController.class);

    private final StaffService staffService;

    public ManagerTeamController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    @RequiresRole("MANAGER")
    @Operation(summary = "Get all team members")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getTeam() {
        log.info("Fetching team members");
        // TODO: Get current user's department and fetch staff
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{id}")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get team member by ID")
    public ResponseEntity<ApiResponse<StaffResponse>> getTeamMemberById(@PathVariable Long id) {
        log.info("Fetching team member: {}", id);
        StaffResponse response = staffService.getStaffById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/department/{departmentId}")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get team by department")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getTeamByDepartment(
            @PathVariable Long departmentId) {
        log.info("Fetching team by department: {}", departmentId);
        List<StaffResponse> staff = staffService.getStaffByDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success(staff));
    }

    @GetMapping("/stats")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get team statistics")
    public ResponseEntity<ApiResponse<Object>> getTeamStats() {
        log.info("Fetching team statistics");
        // TODO: Implement team stats
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}