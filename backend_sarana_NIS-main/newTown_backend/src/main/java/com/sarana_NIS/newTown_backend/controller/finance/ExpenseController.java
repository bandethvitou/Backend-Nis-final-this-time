package com.sarana_NIS.newTown_backend.controller.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.ExpenseRequest;
import com.sarana_NIS.newTown_backend.dto.request.finance.ExpenseApprovalRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.ExpenseResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.ExpenseSummaryResponse;
import com.sarana_NIS.newTown_backend.model.enums.ExpenseCategory;
import com.sarana_NIS.newTown_backend.model.enums.ExpenseStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.finance.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/finance/expenses")
@Tag(name = "Expense Management", description = "Expense management APIs")
public class ExpenseController {

    private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("EXPENSE_CREATE")
    @Operation(summary = "Create a new expense")
    public ResponseEntity<ApiResponse<ExpenseResponse>> createExpense(
            @Valid @RequestBody ExpenseRequest request) {
        log.info("Creating expense: {}", request.getCategory());
        ExpenseResponse response = expenseService.createExpense(request);
        return ResponseEntity.ok(ApiResponse.success("Expense created successfully", response));
    }

    @GetMapping("/{expenseId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get expense by ID")
    public ResponseEntity<ApiResponse<ExpenseResponse>> getExpenseById(
            @PathVariable Long expenseId) {
        log.info("Fetching expense: {}", expenseId);
        ExpenseResponse response = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get expenses by status")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getExpensesByStatus(
            @PathVariable ExpenseStatus status) {
        log.info("Fetching expenses by status: {}", status);
        List<ExpenseResponse> responses = expenseService.getExpensesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/category/{category}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get expenses by category")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getExpensesByCategory(
            @PathVariable ExpenseCategory category) {
        log.info("Fetching expenses by category: {}", category);
        List<ExpenseResponse> responses = expenseService.getExpensesByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/date-range")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get expenses by date range")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getExpensesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        log.info("Fetching expenses from {} to {}", startDate, endDate);
        List<ExpenseResponse> responses = expenseService.getExpensesByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/pending")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get pending expenses")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getPendingExpenses() {
        log.info("Fetching pending expenses");
        List<ExpenseResponse> responses = expenseService.getPendingExpenses();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/summary")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @Operation(summary = "Get expense summary")
    public ResponseEntity<ApiResponse<ExpenseSummaryResponse>> getExpenseSummary() {
        log.info("Fetching expense summary");
        ExpenseSummaryResponse summary = expenseService.getExpenseSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/approve")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("EXPENSE_APPROVE")
    @Operation(summary = "Approve or reject expense")
    public ResponseEntity<ApiResponse<ExpenseResponse>> approveExpense(
            @Valid @RequestBody ExpenseApprovalRequest request) {
        log.info("Approving expense: {}", request.getExpenseId());
        ExpenseResponse response = expenseService.approveExpense(request);
        return ResponseEntity.ok(ApiResponse.success("Expense approved successfully", response));
    }

    @DeleteMapping("/{expenseId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "FINANCE_ADMIN"})
    @RequiresPermission("EXPENSE_DELETE")
    @Operation(summary = "Delete expense")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(
            @PathVariable Long expenseId) {
        log.info("Deleting expense: {}", expenseId);
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok(ApiResponse.success("Expense deleted successfully", null));
    }
}