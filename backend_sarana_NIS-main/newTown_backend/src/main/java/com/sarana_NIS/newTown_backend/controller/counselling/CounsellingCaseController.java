package com.sarana_NIS.newTown_backend.controller.counselling;

import com.sarana_NIS.newTown_backend.dto.request.counselling.CounsellingCaseRequest;
import com.sarana_NIS.newTown_backend.dto.request.counselling.CaseActionRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.counselling.CounsellingCaseResponse;
import com.sarana_NIS.newTown_backend.dto.response.counselling.AtRiskStudentDetailResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.counselling.CounsellingCaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counselling/cases")
@Tag(name = "Counselling Case Management", description = "Counselling case management APIs")
public class CounsellingCaseController {

    private final CounsellingCaseService counsellingCaseService;

    public CounsellingCaseController(CounsellingCaseService counsellingCaseService) {
        this.counsellingCaseService = counsellingCaseService;
    }

    @PostMapping
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("COUNSELLING_CASE_CREATE")
    @Operation(summary = "Create a new counselling case")
    public ResponseEntity<ApiResponse<CounsellingCaseResponse>> createCase(
            @Valid @RequestBody CounsellingCaseRequest request) {
        System.out.println("Creating counselling case for student: " + request.getStudentId());
        CounsellingCaseResponse response = counsellingCaseService.createCase(request);
        return ResponseEntity.ok(ApiResponse.success("Counselling case created successfully", response));
    }

    @GetMapping("/{caseId}")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get counselling case by ID")
    public ResponseEntity<ApiResponse<CounsellingCaseResponse>> getCaseById(
            @PathVariable Long caseId) {
        System.out.println("Fetching counselling case: " + caseId);
        CounsellingCaseResponse response = counsellingCaseService.getCaseById(caseId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/active")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get active counselling cases")
    public ResponseEntity<ApiResponse<List<CounsellingCaseResponse>>> getActiveCases() {
        System.out.println("Fetching active counselling cases");
        List<CounsellingCaseResponse> cases = counsellingCaseService.getActiveCases();
        return ResponseEntity.ok(ApiResponse.success(cases));
    }

    @GetMapping("/student/{studentId}")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get counselling cases by student")
    public ResponseEntity<ApiResponse<List<CounsellingCaseResponse>>> getCasesByStudent(
            @PathVariable Long studentId) {
        System.out.println("Fetching counselling cases for student: " + studentId);
        List<CounsellingCaseResponse> cases = counsellingCaseService.getCasesByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(cases));
    }

    @GetMapping("/at-risk")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get at-risk students")
    public ResponseEntity<ApiResponse<List<AtRiskStudentDetailResponse>>> getAtRiskStudents() {
        System.out.println("Fetching at-risk students");
        List<AtRiskStudentDetailResponse> students = counsellingCaseService.getAtRiskStudents();
        return ResponseEntity.ok(ApiResponse.success(students));
    }

    @PutMapping("/status")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("COUNSELLING_CASE_UPDATE")
    @Operation(summary = "Update counselling case status")
    public ResponseEntity<ApiResponse<CounsellingCaseResponse>> updateCaseStatus(
            @Valid @RequestBody CaseActionRequest request) {
        System.out.println("Updating case status: " + request.getCaseId() + " - " + request.getAction());
        CounsellingCaseResponse response = counsellingCaseService.updateCaseStatus(request);
        return ResponseEntity.ok(ApiResponse.success("Case status updated successfully", response));
    }

    @DeleteMapping("/{caseId}")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("COUNSELLING_CASE_DELETE")
    @Operation(summary = "Delete counselling case")
    public ResponseEntity<ApiResponse<Void>> deleteCase(
            @PathVariable Long caseId) {
        System.out.println("Deleting counselling case: " + caseId);
        return ResponseEntity.ok(ApiResponse.success("Counselling case deleted successfully", null));
    }
}