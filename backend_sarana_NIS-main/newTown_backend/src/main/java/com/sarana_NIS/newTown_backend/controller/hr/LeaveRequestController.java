package com.sarana_NIS.newTown_backend.controller.hr;

import com.sarana_NIS.newTown_backend.dto.request.hr.LeaveRequestDTO;
import com.sarana_NIS.newTown_backend.dto.request.hr.LeaveApprovalRequest;
import com.sarana_NIS.newTown_backend.dto.request.hr.BulkLeaveRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveBalanceResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.hr.LeaveRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hr/leaves")
@Tag(name = "Leave Request Management", description = "Leave request APIs")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PostMapping
    @RequiresRole({"STAFF", "MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @RequiresPermission("LEAVE_CREATE")
    @Operation(summary = "Create a leave request")
    public ResponseEntity<ApiResponse<LeaveResponse>> createLeaveRequest(
            @Valid @RequestBody LeaveRequestDTO request) {
        System.out.println("Creating leave request for staff: " + request.getStaffId());
        LeaveResponse response = leaveRequestService.createLeaveRequest(request);
        return ResponseEntity.ok(ApiResponse.success("Leave request created successfully", response));
    }

    @PostMapping("/bulk")
    @RequiresRole({"HR_ADMIN", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("LEAVE_CREATE")
    @Operation(summary = "Create bulk leave requests")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> createBulkLeaveRequests(
            @Valid @RequestBody BulkLeaveRequest request) {
        System.out.println("Creating bulk leave requests for " + request.getStaffIds().size() + " staff");
        List<LeaveResponse> responses = leaveRequestService.createBulkLeaveRequests(request);
        return ResponseEntity.ok(ApiResponse.success("Bulk leave requests created successfully", responses));
    }

    @GetMapping("/{leaveId}")
    @RequiresRole({"STAFF", "MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get leave request by ID")
    public ResponseEntity<ApiResponse<LeaveResponse>> getLeaveRequestById(
            @PathVariable Long leaveId) {
        System.out.println("Fetching leave request: " + leaveId);
        LeaveResponse response = leaveRequestService.getLeaveRequestById(leaveId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/staff/{staffId}")
    @RequiresRole({"STAFF", "MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get leave requests by staff")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getLeaveRequestsByStaff(
            @PathVariable Long staffId) {
        System.out.println("Fetching leave requests for staff: " + staffId);
        List<LeaveResponse> responses = leaveRequestService.getLeaveRequestsByStaff(staffId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get leave requests by status")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getLeaveRequestsByStatus(
            @PathVariable String status) {
        System.out.println("Fetching leave requests by status: " + status);
        com.sarana_NIS.newTown_backend.model.enums.LeaveStatus leaveStatus =
                com.sarana_NIS.newTown_backend.model.enums.LeaveStatus.valueOf(status);
        List<LeaveResponse> responses = leaveRequestService.getLeaveRequestsByStatus(leaveStatus);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/pending")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get pending leave requests")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getPendingLeaveRequests() {
        System.out.println("Fetching pending leave requests");
        List<LeaveResponse> responses = leaveRequestService.getPendingLeaveRequests();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/staff/{staffId}/balance")
    @RequiresRole({"STAFF", "MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get leave balance for staff")
    public ResponseEntity<ApiResponse<LeaveBalanceResponse>> getLeaveBalance(
            @PathVariable Long staffId) {
        System.out.println("Fetching leave balance for staff: " + staffId);
        LeaveBalanceResponse response = leaveRequestService.getLeaveBalance(staffId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/date-range")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get leave requests by date range")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getLeaveRequestsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        System.out.println("Fetching leave requests from " + startDate + " to " + endDate);
        List<LeaveResponse> responses = leaveRequestService.getLeaveRequestsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/today")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get staff on leave today")
    public ResponseEntity<ApiResponse<List<LeaveResponse>>> getStaffOnLeaveToday() {
        System.out.println("Fetching staff on leave today");
        List<LeaveResponse> responses = leaveRequestService.getStaffOnLeaveToday();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping("/approve")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @RequiresPermission("LEAVE_APPROVE")
    @Operation(summary = "Approve or deny leave request")
    public ResponseEntity<ApiResponse<LeaveResponse>> approveLeave(
            @Valid @RequestBody LeaveApprovalRequest request) {
        System.out.println("Approving leave request: " + request.getLeaveId());
        LeaveResponse response = leaveRequestService.approveLeave(request);
        return ResponseEntity.ok(ApiResponse.success("Leave request approved successfully", response));
    }

    @DeleteMapping("/{leaveId}")
    @RequiresRole({"STAFF", "MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @RequiresPermission("LEAVE_DELETE")
    @Operation(summary = "Delete leave request")
    public ResponseEntity<ApiResponse<Void>> deleteLeaveRequest(
            @PathVariable Long leaveId) {
        System.out.println("Deleting leave request: " + leaveId);
        leaveRequestService.deleteLeaveRequest(leaveId);
        return ResponseEntity.ok(ApiResponse.success("Leave request deleted successfully", null));
    }

    @GetMapping("/stats")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get leave statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLeaveStatistics() {
        System.out.println("Fetching leave statistics");
        Map<String, Object> stats = leaveRequestService.getLeaveStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}