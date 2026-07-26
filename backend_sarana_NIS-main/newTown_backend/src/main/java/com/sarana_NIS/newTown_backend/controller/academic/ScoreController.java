package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.ScoreRequest;
import com.sarana_NIS.newTown_backend.dto.request.academic.ScoreSubmitRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.ScoreSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.Score;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.academic.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic/scores")
@Tag(name = "Score Management", description = "Score entry and management APIs")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("SCORE_ENTER")
    @Operation(summary = "Enter score for a student")
    public ResponseEntity<ApiResponse<Score>> enterScore(
            @Valid @RequestBody ScoreRequest request) {
        System.out.println("Entering score for student: " + request.getStudentId());
        Long currentUserId = 1L;
        Score score = scoreService.enterScore(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Score entered successfully", score));
    }

    @PostMapping("/bulk")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("SCORE_ENTER")
    @Operation(summary = "Submit bulk scores")
    public ResponseEntity<ApiResponse<List<Score>>> submitBulkScores(
            @Valid @RequestBody ScoreSubmitRequest request) {
        System.out.println("Submitting bulk scores for " + request.getScores().size() + " students");
        Long currentUserId = 1L;
        List<Score> scores = scoreService.submitBulkScores(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Bulk scores submitted successfully", scores));
    }

    @GetMapping("/{scoreId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER", "PARENT", "STUDENT"})
    @Operation(summary = "Get score by ID")
    public ResponseEntity<ApiResponse<Score>> getScoreById(
            @PathVariable Long scoreId) {
        System.out.println("Fetching score: " + scoreId);
        Score score = scoreService.getScoreById(scoreId);
        return ResponseEntity.ok(ApiResponse.success(score));
    }

    @GetMapping("/student/{studentId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER", "PARENT", "STUDENT"})
    @Operation(summary = "Get scores by student")
    public ResponseEntity<ApiResponse<List<Score>>> getScoresByStudent(
            @PathVariable Long studentId,
            @RequestParam String term) {
        System.out.println("Fetching scores for student: " + studentId + " term: " + term);
        List<Score> scores = scoreService.getScoresByStudent(studentId, term);
        return ResponseEntity.ok(ApiResponse.success(scores));
    }

    @GetMapping("/class-subject/{classSubjectId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get scores by class subject")
    public ResponseEntity<ApiResponse<List<Score>>> getScoresByClassSubject(
            @PathVariable Long classSubjectId,
            @RequestParam String term) {
        System.out.println("Fetching scores for class subject: " + classSubjectId + " term: " + term);
        List<Score> scores = scoreService.getScoresByClassSubject(classSubjectId, term);
        return ResponseEntity.ok(ApiResponse.success(scores));
    }

    @GetMapping("/summary/{classSubjectId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get score summary")
    public ResponseEntity<ApiResponse<ScoreSummaryResponse>> getScoreSummary(
            @PathVariable Long classSubjectId,
            @RequestParam String term) {
        System.out.println("Fetching score summary for class subject: " + classSubjectId + " term: " + term);
        ScoreSummaryResponse summary = scoreService.getScoreSummary(classSubjectId, term);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PutMapping("/{scoreId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("SCORE_UPDATE")
    @Operation(summary = "Update score")
    public ResponseEntity<ApiResponse<Score>> updateScore(
            @PathVariable Long scoreId,
            @Valid @RequestBody ScoreRequest request) {
        System.out.println("Updating score: " + scoreId);
        Score score = scoreService.updateScore(scoreId, request);
        return ResponseEntity.ok(ApiResponse.success("Score updated successfully", score));
    }

    @PostMapping("/{scoreId}/approve")
    @RequiresRole({"MANAGER", "TOP_MANAGER"})
    @RequiresPermission("SCORE_APPROVE")
    @Operation(summary = "Approve score")
    public ResponseEntity<ApiResponse<Score>> approveScore(
            @PathVariable Long scoreId) {
        System.out.println("Approving score: " + scoreId);
        Long currentUserId = 1L;
        Score score = scoreService.approveScore(scoreId, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Score approved successfully", score));
    }

    @DeleteMapping("/{scoreId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("SCORE_DELETE")
    @Operation(summary = "Delete score")
    public ResponseEntity<ApiResponse<Void>> deleteScore(
            @PathVariable Long scoreId) {
        System.out.println("Deleting score: " + scoreId);
        scoreService.deleteScore(scoreId);
        return ResponseEntity.ok(ApiResponse.success("Score deleted successfully", null));
    }

    @GetMapping("/pending")
    @RequiresRole({"MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get scores pending approval")
    public ResponseEntity<ApiResponse<List<Score>>> getPendingApproval() {
        System.out.println("Fetching scores pending approval");
        List<Score> scores = scoreService.getScoresPendingApproval();
        return ResponseEntity.ok(ApiResponse.success(scores));
    }
}