package com.sarana_NIS.newTown_backend.controller.library;

import com.sarana_NIS.newTown_backend.dto.request.library.CheckoutRequest;
import com.sarana_NIS.newTown_backend.dto.request.library.ReturnBookRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.library.CheckoutResponse;
import com.sarana_NIS.newTown_backend.dto.response.library.OverdueBookResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.library.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library/checkouts")
@Tag(name = "Library Checkout Management", description = "Book checkout/return APIs")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT"})
    @RequiresPermission("BOOK_CHECKOUT")
    @Operation(summary = "Checkout a book")
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkoutBook(
            @Valid @RequestBody CheckoutRequest request) {
        System.out.println("Checking out book: " + request.getBookId() + " to student: " + request.getStudentId());
        CheckoutResponse response = checkoutService.checkoutBook(request);
        return ResponseEntity.ok(ApiResponse.success("Book checked out successfully", response));
    }

    @PostMapping("/return")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT"})
    @RequiresPermission("BOOK_RETURN")
    @Operation(summary = "Return a book")
    public ResponseEntity<ApiResponse<CheckoutResponse>> returnBook(
            @Valid @RequestBody ReturnBookRequest request) {
        System.out.println("Returning book: " + request.getCheckoutId());
        CheckoutResponse response = checkoutService.returnBook(request);
        return ResponseEntity.ok(ApiResponse.success("Book returned successfully", response));
    }

    @GetMapping("/{checkoutId}")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT", "PARENT"})
    @Operation(summary = "Get checkout by ID")
    public ResponseEntity<ApiResponse<CheckoutResponse>> getCheckoutById(
            @PathVariable Long checkoutId) {
        System.out.println("Fetching checkout: " + checkoutId);
        CheckoutResponse response = checkoutService.getCheckoutById(checkoutId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/student/{studentId}/active")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT", "PARENT"})
    @Operation(summary = "Get active checkouts by student")
    public ResponseEntity<ApiResponse<List<CheckoutResponse>>> getActiveCheckoutsByStudent(
            @PathVariable Long studentId) {
        System.out.println("Fetching active checkouts for student: " + studentId);
        List<CheckoutResponse> checkouts = checkoutService.getActiveCheckoutsByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(checkouts));
    }

    @GetMapping("/student/{studentId}/history")
    @RequiresRole({"LIBRARIAN", "TEACHER", "STUDENT", "PARENT"})
    @Operation(summary = "Get checkout history by student")
    public ResponseEntity<ApiResponse<List<CheckoutResponse>>> getCheckoutHistoryByStudent(
            @PathVariable Long studentId) {
        System.out.println("Fetching checkout history for student: " + studentId);
        List<CheckoutResponse> checkouts = checkoutService.getCheckoutHistoryByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(checkouts));
    }

    @GetMapping("/overdue")
    @RequiresRole({"LIBRARIAN", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get overdue books")
    public ResponseEntity<ApiResponse<List<OverdueBookResponse>>> getOverdueBooks() {
        System.out.println("Fetching overdue books");
        List<OverdueBookResponse> overdue = checkoutService.getOverdueBooks();
        return ResponseEntity.ok(ApiResponse.success(overdue));
    }

    @PostMapping("/mark-overdue-fines")
    @RequiresRole({"LIBRARIAN", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("BOOK_UPDATE")
    @Operation(summary = "Mark overdue fines for all overdue books")
    public ResponseEntity<ApiResponse<Void>> markOverdueFines() {
        System.out.println("Marking overdue fines");
        checkoutService.markOverdueFines();
        return ResponseEntity.ok(ApiResponse.success("Overdue fines marked successfully", null));
    }
}