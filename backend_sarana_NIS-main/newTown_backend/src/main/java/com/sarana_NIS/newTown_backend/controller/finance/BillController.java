package com.sarana_NIS.newTown_backend.controller.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.BillRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.BillApprovalRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.BillResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.BillSummaryResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.finance.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@Tag(name = "Bill Management", description = "Bill management APIs")
public class BillController {

    private static final Logger log = LoggerFactory.getLogger(BillController.class);

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get all bills")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getAllBills() {
        log.info("Fetching all bills");
        return ResponseEntity.ok(ApiResponse.success(null)); // TODO: Implement
    }

    @GetMapping("/{id}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get bill by ID")
    public ResponseEntity<ApiResponse<BillResponse>> getBillById(@PathVariable Long id) {
        log.info("Fetching bill: {}", id);
        BillResponse response = billService.getBillById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/pending")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get pending bills")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getPendingBills() {
        log.info("Fetching pending bills");
        List<BillResponse> response = billService.getPendingBills();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/summary")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get bill summary")
    public ResponseEntity<ApiResponse<BillSummaryResponse>> getBillSummary() {
        log.info("Fetching bill summary");
        BillSummaryResponse response = billService.getBillSummary();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("BILL_CREATE")
    @Operation(summary = "Create a new bill")
    public ResponseEntity<ApiResponse<BillResponse>> createBill(
            @Valid @RequestBody BillRequest request) {
        log.info("Creating bill: {}", request.getBillType());
        BillResponse response = billService.createBill(request);
        return ResponseEntity.ok(ApiResponse.success("Bill created successfully", response));
    }

    @PutMapping("/{id}/approve")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("BILL_APPROVE")
    @Operation(summary = "Approve bill")
    public ResponseEntity<ApiResponse<BillResponse>> approveBill(
            @PathVariable Long id,
            @Valid @RequestBody BillApprovalRequest request) {
        log.info("Approving bill: {}", id);
        BillResponse response = billService.approveBill(request);
        return ResponseEntity.ok(ApiResponse.success("Bill approved successfully", response));
    }

    @PutMapping("/{id}/reject")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("BILL_APPROVE")
    @Operation(summary = "Reject bill")
    public ResponseEntity<ApiResponse<BillResponse>> rejectBill(
            @PathVariable Long id,
            @Valid @RequestBody BillApprovalRequest request) {
        log.info("Rejecting bill: {}", id);
        BillResponse response = billService.approveBill(request);
        return ResponseEntity.ok(ApiResponse.success("Bill rejected successfully", response));
    }

    @DeleteMapping("/{id}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("BILL_DELETE")
    @Operation(summary = "Delete bill")
    public ResponseEntity<ApiResponse<Void>> deleteBill(@PathVariable Long id) {
        log.info("Deleting bill: {}", id);
        billService.deleteBill(id);
        return ResponseEntity.ok(ApiResponse.success("Bill deleted successfully", null));
    }
}