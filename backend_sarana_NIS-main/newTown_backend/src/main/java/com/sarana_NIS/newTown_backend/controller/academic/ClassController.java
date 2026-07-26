package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.ClassRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.ClassResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.ClassEnrollmentResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.academic.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic/classes")
@Tag(name = "Class Management", description = "Class management APIs")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("CLASS_CREATE")
    @Operation(summary = "Create a new class")
    public ResponseEntity<ApiResponse<ClassResponse>> createClass(
            @Valid @RequestBody ClassRequest request) {
        System.out.println("Creating class: " + request.getName());
        ClassResponse response = classService.createClass(request);
        return ResponseEntity.ok(ApiResponse.success("Class created successfully", response));
    }

    @GetMapping("/{classId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER", "PARENT"})
    @Operation(summary = "Get class by ID")
    public ResponseEntity<ApiResponse<ClassResponse>> getClassById(
            @PathVariable Long classId) {
        System.out.println("Fetching class: " + classId);
        ClassResponse response = classService.getClassById(classId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get all classes with pagination")
    public ResponseEntity<ApiResponse<PageResponse<ClassResponse>>> getAllClasses(
            Pageable pageable) {
        System.out.println("Fetching all classes");
        PageResponse<ClassResponse> response = classService.getAllClasses(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/level/{levelId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get classes by level")
    public ResponseEntity<ApiResponse<List<ClassResponse>>> getClassesByLevel(
            @PathVariable Long levelId) {
        System.out.println("Fetching classes by level: " + levelId);
        List<ClassResponse> classes = classService.getClassesByLevel(levelId);
        return ResponseEntity.ok(ApiResponse.success(classes));
    }

    @GetMapping("/level/{levelId}/active")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get active classes by level")
    public ResponseEntity<ApiResponse<List<ClassResponse>>> getActiveClassesByLevel(
            @PathVariable Long levelId) {
        System.out.println("Fetching active classes by level: " + levelId);
        List<ClassResponse> classes = classService.getActiveClassesByLevel(levelId);
        return ResponseEntity.ok(ApiResponse.success(classes));
    }

    @GetMapping("/{classId}/enrollment")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get class enrollment details")
    public ResponseEntity<ApiResponse<ClassEnrollmentResponse>> getClassEnrollment(
            @PathVariable Long classId) {
        System.out.println("Fetching enrollment for class: " + classId);
        ClassEnrollmentResponse response = classService.getClassEnrollment(classId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{classId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("CLASS_UPDATE")
    @Operation(summary = "Update class")
    public ResponseEntity<ApiResponse<ClassResponse>> updateClass(
            @PathVariable Long classId,
            @Valid @RequestBody ClassRequest request) {
        System.out.println("Updating class: " + classId);
        ClassResponse response = classService.updateClass(classId, request);
        return ResponseEntity.ok(ApiResponse.success("Class updated successfully", response));
    }

    @DeleteMapping("/{classId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("CLASS_DELETE")
    @Operation(summary = "Delete class (deactivate)")
    public ResponseEntity<ApiResponse<Void>> deleteClass(
            @PathVariable Long classId) {
        System.out.println("Deleting class: " + classId);
        classService.deleteClass(classId);
        return ResponseEntity.ok(ApiResponse.success("Class deactivated successfully", null));
    }
}