package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.SubjectRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.SubjectResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.academic.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic/subjects")
@Tag(name = "Subject Management", description = "Subject management APIs")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("SUBJECT_CREATE")
    @Operation(summary = "Create a new subject")
    public ResponseEntity<ApiResponse<SubjectResponse>> createSubject(
            @Valid @RequestBody SubjectRequest request) {
        System.out.println("Creating subject: " + request.getName());
        SubjectResponse response = subjectService.createSubject(request);
        return ResponseEntity.ok(ApiResponse.success("Subject created successfully", response));
    }

    @GetMapping("/{subjectId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get subject by ID")
    public ResponseEntity<ApiResponse<SubjectResponse>> getSubjectById(
            @PathVariable Long subjectId) {
        System.out.println("Fetching subject: " + subjectId);
        SubjectResponse response = subjectService.getSubjectById(subjectId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/code/{code}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get subject by code")
    public ResponseEntity<ApiResponse<SubjectResponse>> getSubjectByCode(
            @PathVariable String code) {
        System.out.println("Fetching subject by code: " + code);
        SubjectResponse response = subjectService.getSubjectByCode(code);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get all subjects with pagination")
    public ResponseEntity<ApiResponse<PageResponse<SubjectResponse>>> getAllSubjects(
            Pageable pageable) {
        System.out.println("Fetching all subjects");
        PageResponse<SubjectResponse> response = subjectService.getAllSubjects(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/department/{departmentId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get subjects by department")
    public ResponseEntity<ApiResponse<List<SubjectResponse>>> getSubjectsByDepartment(
            @PathVariable Long departmentId) {
        System.out.println("Fetching subjects by department: " + departmentId);
        List<SubjectResponse> subjects = subjectService.getSubjectsByDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success(subjects));
    }

    @PutMapping("/{subjectId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("SUBJECT_UPDATE")
    @Operation(summary = "Update subject")
    public ResponseEntity<ApiResponse<SubjectResponse>> updateSubject(
            @PathVariable Long subjectId,
            @Valid @RequestBody SubjectRequest request) {
        System.out.println("Updating subject: " + subjectId);
        SubjectResponse response = subjectService.updateSubject(subjectId, request);
        return ResponseEntity.ok(ApiResponse.success("Subject updated successfully", response));
    }

    @DeleteMapping("/{subjectId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("SUBJECT_DELETE")
    @Operation(summary = "Delete subject")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(
            @PathVariable Long subjectId) {
        System.out.println("Deleting subject: " + subjectId);
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.ok(ApiResponse.success("Subject deleted successfully", null));
    }
}