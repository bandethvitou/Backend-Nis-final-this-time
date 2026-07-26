package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.AcademicLevelRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.AcademicLevelResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.academic.AcademicLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic/levels")
@Tag(name = "Academic Level Management", description = "Academic level APIs")
public class AcademicLevelController {

    private final AcademicLevelService academicLevelService;

    public AcademicLevelController(AcademicLevelService academicLevelService) {
        this.academicLevelService = academicLevelService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("ACADEMIC_LEVEL_CREATE")
    @Operation(summary = "Create a new academic level")
    public ResponseEntity<ApiResponse<AcademicLevelResponse>> createLevel(
            @Valid @RequestBody AcademicLevelRequest request) {
        System.out.println("Creating academic level: " + request.getName());
        AcademicLevelResponse response = academicLevelService.createLevel(request);
        return ResponseEntity.ok(ApiResponse.success("Academic level created successfully", response));
    }

    @GetMapping("/{levelId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get academic level by ID")
    public ResponseEntity<ApiResponse<AcademicLevelResponse>> getLevelById(
            @PathVariable Long levelId) {
        System.out.println("Fetching academic level: " + levelId);
        AcademicLevelResponse response = academicLevelService.getLevelById(levelId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get all academic levels")
    public ResponseEntity<ApiResponse<List<AcademicLevelResponse>>> getAllLevels() {
        System.out.println("Fetching all academic levels");
        List<AcademicLevelResponse> levels = academicLevelService.getAllLevels();
        return ResponseEntity.ok(ApiResponse.success(levels));
    }

    @GetMapping("/page")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get academic levels with pagination")
    public ResponseEntity<ApiResponse<PageResponse<AcademicLevelResponse>>> getLevels(
            Pageable pageable) {
        System.out.println("Fetching academic levels with pagination");
        PageResponse<AcademicLevelResponse> response = academicLevelService.getLevels(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/age/{age}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get academic levels for a specific age")
    public ResponseEntity<ApiResponse<List<AcademicLevelResponse>>> getLevelsForAge(
            @PathVariable Integer age) {
        System.out.println("Fetching levels for age: " + age);
        List<AcademicLevelResponse> levels = academicLevelService.getLevelsForAge(age);
        return ResponseEntity.ok(ApiResponse.success(levels));
    }

    @PutMapping("/{levelId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("ACADEMIC_LEVEL_UPDATE")
    @Operation(summary = "Update academic level")
    public ResponseEntity<ApiResponse<AcademicLevelResponse>> updateLevel(
            @PathVariable Long levelId,
            @Valid @RequestBody AcademicLevelRequest request) {
        System.out.println("Updating academic level: " + levelId);
        AcademicLevelResponse response = academicLevelService.updateLevel(levelId, request);
        return ResponseEntity.ok(ApiResponse.success("Academic level updated successfully", response));
    }

    @DeleteMapping("/{levelId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("ACADEMIC_LEVEL_DELETE")
    @Operation(summary = "Delete academic level")
    public ResponseEntity<ApiResponse<Void>> deleteLevel(
            @PathVariable Long levelId) {
        System.out.println("Deleting academic level: " + levelId);
        academicLevelService.deleteLevel(levelId);
        return ResponseEntity.ok(ApiResponse.success("Academic level deleted successfully", null));
    }
}