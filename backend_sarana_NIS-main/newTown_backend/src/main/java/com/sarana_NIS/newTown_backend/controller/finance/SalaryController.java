package com.sarana_NIS.newTown_backend.controller.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.SalaryRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.SalaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.finance.SalarySummaryResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.finance.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/salaries")
@Tag(name = "Salary Management", description = "Salary management APIs")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "HR_ADMIN", "FINANCE_ADMIN"})
    @RequiresPermission("SALARY_CREATE")
    @Operation(summary = "Create a new salary record")
    public ResponseEntity<ApiResponse<SalaryResponse>> createSalary(
            @Valid @RequestBody SalaryRequest request) {
        System.out.println("Creating salary for staff: " + request.getStaffId() + " for month: " + request.getMonth());
        SalaryResponse response = salaryService.createSalary(request);
        return ResponseEntity.ok(ApiResponse.success("Salary created successfully", response));
    }

    @GetMapping("/{salaryId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "HR_ADMIN", "FINANCE_ADMIN"})
    @Operation(summary = "Get salary by ID")
    public ResponseEntity<ApiResponse<SalaryResponse>> getSalaryById(
            @PathVariable Long salaryId) {
        System.out.println("Fetching salary: " + salaryId);
        SalaryResponse response = salaryService.getSalaryById(salaryId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/staff/{staffId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "HR_ADMIN", "FINANCE_ADMIN"})
    @Operation(summary = "Get salaries by staff")
    public ResponseEntity<ApiResponse<List<SalaryResponse>>> getSalariesByStaff(
            @PathVariable Long staffId) {
        System.out.println("Fetching salaries for staff: " + staffId);
        List<SalaryResponse> responses = salaryService.getSalariesByStaff(staffId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/month/{month}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "HR_ADMIN", "FINANCE_ADMIN"})
    @Operation(summary = "Get salaries by month")
    public ResponseEntity<ApiResponse<List<SalaryResponse>>> getSalariesByMonth(
            @PathVariable String month) {
        System.out.println("Fetching salaries for month: " + month);
        List<SalaryResponse> responses = salaryService.getSalariesByMonth(month);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/summary/{month}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "HR_ADMIN", "FINANCE_ADMIN"})
    @Operation(summary = "Get salary summary for month")
    public ResponseEntity<ApiResponse<SalarySummaryResponse>> getSalarySummary(
            @PathVariable String month) {
        System.out.println("Fetching salary summary for month: " + month);
        SalarySummaryResponse summary = salaryService.getSalarySummary(month);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/{salaryId}/paid")
    @RequiresRole({"FINANCE_ADMIN"})
    @RequiresPermission("SALARY_UPDATE")
    @Operation(summary = "Mark salary as paid")
    public ResponseEntity<ApiResponse<SalaryResponse>> markAsPaid(
            @PathVariable Long salaryId) {
        System.out.println("Marking salary as paid: " + salaryId);
        SalaryResponse response = salaryService.markAsPaid(salaryId);
        return ResponseEntity.ok(ApiResponse.success("Salary marked as paid successfully", response));
    }

    @DeleteMapping("/{salaryId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "HR_ADMIN", "FINANCE_ADMIN"})
    @RequiresPermission("SALARY_DELETE")
    @Operation(summary = "Delete salary")
    public ResponseEntity<ApiResponse<Void>> deleteSalary(
            @PathVariable Long salaryId) {
        System.out.println("Deleting salary: " + salaryId);
        salaryService.deleteSalary(salaryId);
        return ResponseEntity.ok(ApiResponse.success("Salary deleted successfully", null));
    }
}