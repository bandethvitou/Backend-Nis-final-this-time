package com.sarana_NIS.newTown_backend.controller.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.PaymentRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.PaymentVerificationRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.PaymentResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.PaymentHistoryResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.finance.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/payments")
@Tag(name = "Payment Management", description = "Payment management APIs")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("PAYMENT_CREATE")
    @Operation(summary = "Create a new payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody PaymentRequest request) {
        System.out.println("Creating payment for student: " + request.getStudentId());
        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.ok(ApiResponse.success("Payment created successfully", response));
    }

    @GetMapping("/{paymentId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN", "PARENT"})
    @Operation(summary = "Get payment by ID")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable Long paymentId) {
        System.out.println("Fetching payment: " + paymentId);
        PaymentResponse response = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/student/{studentId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN", "PARENT"})
    @Operation(summary = "Get payments by student")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByStudent(
            @PathVariable Long studentId) {
        System.out.println("Fetching payments for student: " + studentId);
        List<PaymentResponse> responses = paymentService.getPaymentsByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/history/{studentId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN", "PARENT"})
    @Operation(summary = "Get payment history for student")
    public ResponseEntity<ApiResponse<PaymentHistoryResponse>> getPaymentHistory(
            @PathVariable Long studentId) {
        System.out.println("Fetching payment history for student: " + studentId);
        PaymentHistoryResponse response = paymentService.getPaymentHistory(studentId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/pending")
    @RequiresRole({"FINANCE_ADMIN"})
    @Operation(summary = "Get pending verifications")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPendingVerifications() {
        System.out.println("Fetching pending verifications");
        List<PaymentResponse> responses = paymentService.getPendingVerifications();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping("/verify")
    @RequiresRole({"FINANCE_ADMIN"})
    @RequiresPermission("PAYMENT_VERIFY")
    @Operation(summary = "Verify payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> verifyPayment(
            @Valid @RequestBody PaymentVerificationRequest request) {
        System.out.println("Verifying payment: " + request.getPaymentId());
        PaymentResponse response = paymentService.verifyPayment(request);
        return ResponseEntity.ok(ApiResponse.success("Payment verified successfully", response));
    }

    @PostMapping("/{paymentId}/reject")
    @RequiresRole({"FINANCE_ADMIN"})
    @RequiresPermission("PAYMENT_VERIFY")
    @Operation(summary = "Reject payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> rejectPayment(
            @PathVariable Long paymentId,
            @RequestParam String reason) {
        System.out.println("Rejecting payment: " + paymentId);
        PaymentResponse response = paymentService.rejectPayment(paymentId, reason);
        return ResponseEntity.ok(ApiResponse.success("Payment rejected successfully", response));
    }

    @DeleteMapping("/{paymentId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("PAYMENT_DELETE")
    @Operation(summary = "Delete payment")
    public ResponseEntity<ApiResponse<Void>> deletePayment(
            @PathVariable Long paymentId) {
        System.out.println("Deleting payment: " + paymentId);
        return ResponseEntity.ok(ApiResponse.success("Payment deleted successfully", null));
    }
}