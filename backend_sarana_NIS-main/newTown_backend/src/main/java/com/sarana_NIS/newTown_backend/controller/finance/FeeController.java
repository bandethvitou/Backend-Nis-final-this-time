package com.sarana_NIS.newTown_backend.controller.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.FeeRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.FeeBulkRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.FeeResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.FeeSummaryResponse;
import com.sarana_NIS.newTown_backend.model.enums.FeeStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.finance.FeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/fees")
@Tag(name = "Fee Management", description = "Fee management APIs")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("FEE_CREATE")
    @Operation(summary = "Create a new fee")
    public ResponseEntity<ApiResponse<FeeResponse>> createFee(
            @Valid @RequestBody FeeRequest request) {
        System.out.println("Creating fee for student: " + request.getStudentId());
        FeeResponse response = feeService.createFee(request);
        return ResponseEntity.ok(ApiResponse.success("Fee created successfully", response));
    }

    @PostMapping("/bulk")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("FEE_CREATE")
    @Operation(summary = "Create bulk fees")
    public ResponseEntity<ApiResponse<List<FeeResponse>>> createBulkFees(
            @Valid @RequestBody FeeBulkRequest request) {
        System.out.println("Creating bulk fees for " + request.getStudentIds().size() + " students");
        List<FeeResponse> responses = feeService.createBulkFees(request);
        return ResponseEntity.ok(ApiResponse.success("Bulk fees created successfully", responses));
    }

    @GetMapping("/{feeId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN", "PARENT"})
    @Operation(summary = "Get fee by ID")
    public ResponseEntity<ApiResponse<FeeResponse>> getFeeById(
            @PathVariable Long feeId) {
        System.out.println("Fetching fee: " + feeId);
        FeeResponse response = feeService.getFeeById(feeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/student/{studentId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN", "PARENT"})
    @Operation(summary = "Get fees by student")
    public ResponseEntity<ApiResponse<List<FeeResponse>>> getFeesByStudent(
            @PathVariable Long studentId) {
        System.out.println("Fetching fees for student: " + studentId);
        List<FeeResponse> responses = feeService.getFeesByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get fees by status")
    public ResponseEntity<ApiResponse<List<FeeResponse>>> getFeesByStatus(
            @PathVariable FeeStatus status) {
        System.out.println("Fetching fees by status: " + status);
        List<FeeResponse> responses = feeService.getFeesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/overdue")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get overdue fees")
    public ResponseEntity<ApiResponse<List<FeeResponse>>> getOverdueFees() {
        System.out.println("Fetching overdue fees");
        List<FeeResponse> responses = feeService.getOverdueFees();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/summary")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get fee summary")
    public ResponseEntity<ApiResponse<FeeSummaryResponse>> getFeeSummary() {
        System.out.println("Fetching fee summary");
        FeeSummaryResponse summary = feeService.getFeeSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/{feeId}/paid")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("FEE_UPDATE")
    @Operation(summary = "Mark fee as paid")
    public ResponseEntity<ApiResponse<Void>> markAsPaid(
            @PathVariable Long feeId) {
        System.out.println("Marking fee as paid: " + feeId);
        feeService.markAsPaid(feeId);
        return ResponseEntity.ok(ApiResponse.success("Fee marked as paid successfully", null));
    }

    @DeleteMapping("/{feeId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("FEE_DELETE")
    @Operation(summary = "Delete fee")
    public ResponseEntity<ApiResponse<Void>> deleteFee(
            @PathVariable Long feeId) {
        System.out.println("Deleting fee: " + feeId);
        feeService.deleteFee(feeId);
        return ResponseEntity.ok(ApiResponse.success("Fee deleted successfully", null));
    }
}