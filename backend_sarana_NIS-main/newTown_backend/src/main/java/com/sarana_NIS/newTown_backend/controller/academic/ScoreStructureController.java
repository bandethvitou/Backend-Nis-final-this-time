package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.ScoreStructureRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.ScoreStructureResponse;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.academic.ScoreStructureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic/score-structures")
@Tag(name = "Score Structure Management", description = "Score structure configuration APIs")
public class ScoreStructureController {

    private final ScoreStructureService scoreStructureService;

    public ScoreStructureController(ScoreStructureService scoreStructureService) {
        this.scoreStructureService = scoreStructureService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("SCORE_STRUCTURE_CREATE")
    @Operation(summary = "Create a new score structure")
    public ResponseEntity<ApiResponse<ScoreStructureResponse>> createScoreStructure(
            @Valid @RequestBody ScoreStructureRequest request) {
        System.out.println("Creating score structure for level: " + request.getLevel());
        ScoreStructureResponse response = scoreStructureService.createScoreStructure(request);
        return ResponseEntity.ok(ApiResponse.success("Score structure created successfully", response));
    }

    @GetMapping("/{structureId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get score structure by ID")
    public ResponseEntity<ApiResponse<ScoreStructureResponse>> getScoreStructureById(
            @PathVariable Long structureId) {
        System.out.println("Fetching score structure: " + structureId);
        ScoreStructureResponse response = scoreStructureService.getScoreStructureById(structureId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/level/{level}/year/{academicYear}/term/{term}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get score structures by level, academic year, and term")
    public ResponseEntity<ApiResponse<List<ScoreStructureResponse>>> getByLevelAndYearAndTerm(
            @PathVariable Level level,
            @PathVariable String academicYear,
            @PathVariable String term) {
        System.out.println("Fetching score structures for level: " + level + " year: " + academicYear + " term: " + term);
        List<ScoreStructureResponse> structures = scoreStructureService.getByLevelAndAcademicYearAndTerm(level, academicYear, term);
        return ResponseEntity.ok(ApiResponse.success(structures));
    }

    @GetMapping("/class/{classId}/year/{academicYear}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get score structure by class and academic year")
    public ResponseEntity<ApiResponse<ScoreStructureResponse>> getByClassAndAcademicYear(
            @PathVariable Long classId,
            @PathVariable String academicYear) {
        System.out.println("Fetching score structure for class: " + classId + " year: " + academicYear);
        ScoreStructureResponse response = scoreStructureService.getByClassAndAcademicYear(classId, academicYear);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/class/{classId}/subject/{subjectId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get score structure by class and subject")
    public ResponseEntity<ApiResponse<ScoreStructureResponse>> getByClassAndSubject(
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        System.out.println("Fetching score structure for class: " + classId + " subject: " + subjectId);
        ScoreStructureResponse response = scoreStructureService.getByClassAndSubject(classId, subjectId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/active")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get all active score structures")
    public ResponseEntity<ApiResponse<List<ScoreStructureResponse>>> getActiveStructures() {
        System.out.println("Fetching active score structures");
        List<ScoreStructureResponse> structures = scoreStructureService.getActiveStructures();
        return ResponseEntity.ok(ApiResponse.success(structures));
    }

    @GetMapping("/active/level/{level}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER", "TEACHER"})
    @Operation(summary = "Get active score structures by level")
    public ResponseEntity<ApiResponse<List<ScoreStructureResponse>>> getActiveByLevel(
            @PathVariable Level level) {
        System.out.println("Fetching active score structures for level: " + level);
        List<ScoreStructureResponse> structures = scoreStructureService.getActiveByLevel(level);
        return ResponseEntity.ok(ApiResponse.success(structures));
    }

    @PutMapping("/{structureId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("SCORE_STRUCTURE_UPDATE")
    @Operation(summary = "Update score structure")
    public ResponseEntity<ApiResponse<ScoreStructureResponse>> updateScoreStructure(
            @PathVariable Long structureId,
            @Valid @RequestBody ScoreStructureRequest request) {
        System.out.println("Updating score structure: " + structureId);
        ScoreStructureResponse response = scoreStructureService.updateScoreStructure(structureId, request);
        return ResponseEntity.ok(ApiResponse.success("Score structure updated successfully", response));
    }

    @DeleteMapping("/{structureId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER"})
    @RequiresPermission("SCORE_STRUCTURE_DELETE")
    @Operation(summary = "Delete score structure")
    public ResponseEntity<ApiResponse<Void>> deleteScoreStructure(
            @PathVariable Long structureId) {
        System.out.println("Deleting score structure: " + structureId);
        scoreStructureService.deleteScoreStructure(structureId);
        return ResponseEntity.ok(ApiResponse.success("Score structure deleted successfully", null));
    }
}