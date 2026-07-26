package com.sarana_NIS.newTown_backend.controller.counselling;

import com.sarana_NIS.newTown_backend.dto.request.counselling.CounsellingSessionRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.counselling.CounsellingSessionResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.counselling.CounsellingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counselling/sessions")
@Tag(name = "Counselling Session Management", description = "Counselling session management APIs")
public class CounsellingSessionController {

    private final CounsellingSessionService counsellingSessionService;

    public CounsellingSessionController(CounsellingSessionService counsellingSessionService) {
        this.counsellingSessionService = counsellingSessionService;
    }

    @PostMapping
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("COUNSELLING_SESSION_CREATE")
    @Operation(summary = "Create a new counselling session")
    public ResponseEntity<ApiResponse<CounsellingSessionResponse>> createSession(
            @Valid @RequestBody CounsellingSessionRequest request) {
        System.out.println("Creating counselling session for student: " + request.getStudentId());
        CounsellingSessionResponse response = counsellingSessionService.createSession(request);
        return ResponseEntity.ok(ApiResponse.success("Counselling session created successfully", response));
    }

    @GetMapping("/{sessionId}")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get counselling session by ID")
    public ResponseEntity<ApiResponse<CounsellingSessionResponse>> getSessionById(
            @PathVariable Long sessionId) {
        System.out.println("Fetching counselling session: " + sessionId);
        CounsellingSessionResponse response = counsellingSessionService.getSessionById(sessionId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/student/{studentId}")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get counselling sessions by student")
    public ResponseEntity<ApiResponse<List<CounsellingSessionResponse>>> getSessionsByStudent(
            @PathVariable Long studentId) {
        System.out.println("Fetching counselling sessions for student: " + studentId);
        List<CounsellingSessionResponse> sessions = counsellingSessionService.getSessionsByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success(sessions));
    }

    @GetMapping("/case/{caseId}")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get counselling sessions by case")
    public ResponseEntity<ApiResponse<List<CounsellingSessionResponse>>> getSessionsByCase(
            @PathVariable Long caseId) {
        System.out.println("Fetching counselling sessions for case: " + caseId);
        List<CounsellingSessionResponse> sessions = counsellingSessionService.getSessionsByCase(caseId);
        return ResponseEntity.ok(ApiResponse.success(sessions));
    }

    @PatchMapping("/{sessionId}/notes")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("COUNSELLING_SESSION_UPDATE")
    @Operation(summary = "Update session notes")
    public ResponseEntity<ApiResponse<CounsellingSessionResponse>> updateSessionNotes(
            @PathVariable Long sessionId,
            @RequestParam String notes) {
        System.out.println("Updating session notes: " + sessionId);
        CounsellingSessionResponse response = counsellingSessionService.updateSessionNotes(sessionId, notes);
        return ResponseEntity.ok(ApiResponse.success("Session notes updated successfully", response));
    }

    @DeleteMapping("/{sessionId}")
    @RequiresRole({"COUNSELLOR", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("COUNSELLING_SESSION_DELETE")
    @Operation(summary = "Delete counselling session")
    public ResponseEntity<ApiResponse<Void>> deleteSession(
            @PathVariable Long sessionId) {
        System.out.println("Deleting counselling session: " + sessionId);
        return ResponseEntity.ok(ApiResponse.success("Counselling session deleted successfully", null));
    }
}