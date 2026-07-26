package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.ClassSubjectRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.ClassSubjectResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.academic.ClassSubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic/class-subjects")
@Tag(name = "Class Subject Management", description = "Class subject assignment APIs")
public class ClassSubjectController {

    private static final Logger log = LoggerFactory.getLogger(ClassSubjectController.class);

    private final ClassSubjectService classSubjectService;

    public ClassSubjectController(ClassSubjectService classSubjectService) {
        this.classSubjectService = classSubjectService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("CLASS_SUBJECT_ASSIGN")
    @Operation(summary = "Assign a subject to a class")
    public ResponseEntity<ApiResponse<ClassSubjectResponse>> assignSubject(
            @Valid @RequestBody ClassSubjectRequest request) {
        log.info("Assigning subject {} to class {}", request.getSubjectId(), request.getClassId());
        ClassSubjectResponse response = classSubjectService.assignSubject(request);
        return ResponseEntity.ok(ApiResponse.success("Subject assigned successfully", response));
    }

    @GetMapping("/class/{classId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get subjects by class")
    public ResponseEntity<ApiResponse<List<ClassSubjectResponse>>> getSubjectsByClass(
            @PathVariable Long classId) {
        log.info("Fetching subjects by class: {}", classId);
        List<ClassSubjectResponse> response = classSubjectService.getSubjectsByClass(classId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/teacher/{teacherId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get subjects by teacher")
    public ResponseEntity<ApiResponse<List<ClassSubjectResponse>>> getSubjectsByTeacher(
            @PathVariable Long teacherId) {
        log.info("Fetching subjects by teacher: {}", teacherId);
        List<ClassSubjectResponse> response = classSubjectService.getSubjectsByTeacher(teacherId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("CLASS_SUBJECT_REMOVE")
    @Operation(summary = "Remove a subject from a class")
    public ResponseEntity<ApiResponse<Void>> removeSubject(
            @PathVariable Long id) {
        log.info("Removing class subject: {}", id);
        classSubjectService.removeSubject(id);
        return ResponseEntity.ok(ApiResponse.success("Subject removed successfully", null));
    }
}