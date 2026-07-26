package com.sarana_NIS.newTown_backend.controller.manager;

import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveSummaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.StaffResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.hr.LeaveRequestService;
import com.sarana_NIS.newTown_backend.service.user.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/manager/staff-reports")
@Tag(name = "Staff Report Management", description = "Staff report management APIs")
public class StaffReportController {

    private static final Logger log = LoggerFactory.getLogger(StaffReportController.class);

    private final StaffService staffService;
    private final LeaveRequestService leaveRequestService;

    public StaffReportController(StaffService staffService, LeaveRequestService leaveRequestService) {
        this.staffService = staffService;
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping
    @RequiresRole("MANAGER")
    @Operation(summary = "Get all staff reports")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getAllStaffReports() {
        log.info("Fetching all staff reports");
        return ResponseEntity.ok(ApiResponse.success(null)); // TODO: Implement
    }

    @GetMapping("/{id}")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get staff report by ID")
    public ResponseEntity<ApiResponse<StaffResponse>> getStaffReportById(@PathVariable Long id) {
        log.info("Fetching staff report: {}", id);
        StaffResponse response = staffService.getStaffById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/department/{departmentId}")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get staff reports by department")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getStaffReportsByDepartment(
            @PathVariable Long departmentId) {
        log.info("Fetching staff reports by department: {}", departmentId);
        List<StaffResponse> staff = staffService.getStaffByDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success(staff));
    }

    @GetMapping("/leaves")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get leave reports")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getLeaveReports() {
        log.info("Fetching leave reports");
        List<LeaveResponse> leaves = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok(ApiResponse.success(leaves));
    }

    @GetMapping("/leaves/pending")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get pending leave requests")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getPendingLeaves() {
        log.info("Fetching pending leave requests");
        List<LeaveResponse> leaves = leaveRequestService.getPendingLeaveRequests();
        return ResponseEntity.ok(ApiResponse.success(leaves));
    }

    @GetMapping("/leaves/summary")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get leave summary")
    public ResponseEntity<ApiResponse<LeaveSummaryResponse>> getLeaveSummary() {
        log.info("Fetching leave summary");
        // TODO: Implement leave summary
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/on-leave")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get staff on leave today")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getStaffOnLeaveToday() {
        log.info("Fetching staff on leave today");
        List<LeaveResponse> leaves = leaveRequestService.getStaffOnLeaveToday();
        return ResponseEntity.ok(ApiResponse.success(leaves));
    }

    @GetMapping("/stats")
    @RequiresRole("MANAGER")
    @Operation(summary = "Get staff report statistics")
    public ResponseEntity<ApiResponse<Object>> getStaffReportStats() {
        log.info("Fetching staff report statistics");
        // TODO: Implement staff report stats
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}