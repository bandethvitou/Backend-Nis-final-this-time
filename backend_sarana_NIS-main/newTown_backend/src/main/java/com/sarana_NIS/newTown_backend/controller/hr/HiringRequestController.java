package com.sarana_NIS.newTown_backend.controller.hr;

import com.sarana_NIS.newTown_backend.dto.request.hr.HiringRequestDTO;
import com.sarana_NIS.newTown_backend.dto.request.hr.HiringApprovalRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.HiringResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.hr.HiringRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hr/hiring")
@Tag(name = "Hiring Request Management", description = "Hiring request APIs")
public class HiringRequestController {

    private final HiringRequestService hiringRequestService;

    public HiringRequestController(HiringRequestService hiringRequestService) {
        this.hiringRequestService = hiringRequestService;
    }

    @PostMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @RequiresPermission("HIRING_CREATE")
    @Operation(summary = "Create a hiring request")
    public ResponseEntity<ApiResponse<HiringResponse>> createHiringRequest(
            @Valid @RequestBody HiringRequestDTO request) {
        System.out.println("Creating hiring request for position: " + request.getPosition());
        HiringResponse response = hiringRequestService.createHiringRequest(request);
        return ResponseEntity.ok(ApiResponse.success("Hiring request created successfully", response));
    }

    @GetMapping("/{hiringId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get hiring request by ID")
    public ResponseEntity<ApiResponse<HiringResponse>> getHiringRequestById(
            @PathVariable Long hiringId) {
        System.out.println("Fetching hiring request: " + hiringId);
        HiringResponse response = hiringRequestService.getHiringRequestById(hiringId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/pending")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get pending hiring requests")
    public ResponseEntity<ApiResponse<List<HiringResponse>>> getPendingHiringRequests() {
        System.out.println("Fetching pending hiring requests");
        List<HiringResponse> responses = hiringRequestService.getPendingHiringRequests();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/department/{departmentId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @Operation(summary = "Get hiring requests by department")
    public ResponseEntity<ApiResponse<List<HiringResponse>>> getHiringRequestsByDepartment(
            @PathVariable Long departmentId) {
        System.out.println("Fetching hiring requests by department: " + departmentId);
        List<HiringResponse> responses = hiringRequestService.getHiringRequestsByDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping("/approve")
    @RequiresRole({"TOP_MANAGER", "HR_ADMIN"})
    @RequiresPermission("HIRING_APPROVE")
    @Operation(summary = "Approve or reject hiring request")
    public ResponseEntity<ApiResponse<HiringResponse>> approveHiringRequest(
            @Valid @RequestBody HiringApprovalRequest request) {
        System.out.println("Approving hiring request: " + request.getHiringId());
        HiringResponse response = hiringRequestService.approveHiringRequest(request);
        return ResponseEntity.ok(ApiResponse.success("Hiring request approved successfully", response));
    }

    @DeleteMapping("/{hiringId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "HR_ADMIN"})
    @RequiresPermission("HIRING_DELETE")
    @Operation(summary = "Delete hiring request")
    public ResponseEntity<ApiResponse<Void>> deleteHiringRequest(
            @PathVariable Long hiringId) {
        System.out.println("Deleting hiring request: " + hiringId);
        hiringRequestService.deleteHiringRequest(hiringId);
        return ResponseEntity.ok(ApiResponse.success("Hiring request deleted successfully", null));
    }
}