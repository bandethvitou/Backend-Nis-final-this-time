package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.LessonPlanRequest;
import com.sarana_NIS.newTown_backend.dto.request.academic.LessonPlanSubmitRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.LessonPlan;
import com.sarana_NIS.newTown_backend.model.enums.LessonPlanStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.academic.LessonPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/academic/lesson-plans")
@Tag(name = "Lesson Plan Management", description = "Lesson plan management APIs")
public class LessonPlanController {

    private final LessonPlanService lessonPlanService;

    public LessonPlanController(LessonPlanService lessonPlanService) {
        this.lessonPlanService = lessonPlanService;
    }

    @PostMapping
    @RequiresRole({"TEACHER"})
    @RequiresPermission("LESSON_PLAN_CREATE")
    @Operation(summary = "Create a new lesson plan")
    public ResponseEntity<ApiResponse<LessonPlan>> createLessonPlan(
            @Valid @RequestBody LessonPlanRequest request) {
        System.out.println("Creating lesson plan for class subject: " + request.getClassSubjectId());
        Long currentUserId = 1L;
        LessonPlan lessonPlan = lessonPlanService.createLessonPlan(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Lesson plan created successfully", lessonPlan));
    }

    @PostMapping("/submit")
    @RequiresRole({"TEACHER"})
    @RequiresPermission("LESSON_PLAN_SUBMIT")
    @Operation(summary = "Submit lesson plan for review")
    public ResponseEntity<ApiResponse<LessonPlan>> submitLessonPlan(
            @Valid @RequestBody LessonPlanSubmitRequest request) {
        System.out.println("Submitting lesson plan: " + request.getLessonPlanId());
        LessonPlan lessonPlan = lessonPlanService.submitLessonPlan(request);
        return ResponseEntity.ok(ApiResponse.success("Lesson plan submitted successfully", lessonPlan));
    }

    @PostMapping("/{lessonPlanId}/approve")
    @RequiresRole({"MANAGER", "TOP_MANAGER"})
    @RequiresPermission("LESSON_PLAN_APPROVE")
    @Operation(summary = "Approve lesson plan")
    public ResponseEntity<ApiResponse<LessonPlan>> approveLessonPlan(
            @PathVariable Long lessonPlanId) {
        System.out.println("Approving lesson plan: " + lessonPlanId);
        Long currentUserId = 1L;
        LessonPlan lessonPlan = lessonPlanService.approveLessonPlan(lessonPlanId, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Lesson plan approved successfully", lessonPlan));
    }

    @PostMapping("/{lessonPlanId}/revision")
    @RequiresRole({"MANAGER", "TOP_MANAGER"})
    @RequiresPermission("LESSON_PLAN_REVISE")
    @Operation(summary = "Request revision for lesson plan")
    public ResponseEntity<ApiResponse<LessonPlan>> requestRevision(
            @PathVariable Long lessonPlanId,
            @Valid @RequestBody LessonPlanSubmitRequest request) {
        System.out.println("Requesting revision for lesson plan: " + lessonPlanId);
        LessonPlan lessonPlan = lessonPlanService.requestRevision(request);
        return ResponseEntity.ok(ApiResponse.success("Revision requested successfully", lessonPlan));
    }

    @GetMapping("/{lessonPlanId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get lesson plan by ID")
    public ResponseEntity<ApiResponse<LessonPlan>> getLessonPlanById(
            @PathVariable Long lessonPlanId) {
        System.out.println("Fetching lesson plan: " + lessonPlanId);
        LessonPlan lessonPlan = lessonPlanService.getLessonPlanById(lessonPlanId);
        return ResponseEntity.ok(ApiResponse.success(lessonPlan));
    }

    @GetMapping("/class-subject/{classSubjectId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get lesson plans by class subject")
    public ResponseEntity<ApiResponse<List<LessonPlan>>> getLessonPlansByClassSubject(
            @PathVariable Long classSubjectId) {
        System.out.println("Fetching lesson plans for class subject: " + classSubjectId);
        List<LessonPlan> lessonPlans = lessonPlanService.getLessonPlansByClassSubject(classSubjectId);
        return ResponseEntity.ok(ApiResponse.success(lessonPlans));
    }

    @GetMapping("/teacher/{teacherId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get lesson plans by teacher")
    public ResponseEntity<ApiResponse<List<LessonPlan>>> getLessonPlansByTeacher(
            @PathVariable Long teacherId) {
        System.out.println("Fetching lesson plans by teacher: " + teacherId);
        List<LessonPlan> lessonPlans = lessonPlanService.getLessonPlansByTeacher(teacherId);
        return ResponseEntity.ok(ApiResponse.success(lessonPlans));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get lesson plans by status")
    public ResponseEntity<ApiResponse<List<LessonPlan>>> getLessonPlansByStatus(
            @PathVariable LessonPlanStatus status) {
        System.out.println("Fetching lesson plans by status: " + status);
        List<LessonPlan> lessonPlans = lessonPlanService.getLessonPlansByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(lessonPlans));
    }

    @GetMapping("/pending")
    @RequiresRole({"MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get lesson plans pending approval")
    public ResponseEntity<ApiResponse<List<LessonPlan>>> getPendingApproval() {
        System.out.println("Fetching lesson plans pending approval");
        List<LessonPlan> lessonPlans = lessonPlanService.getPendingApproval();
        return ResponseEntity.ok(ApiResponse.success(lessonPlans));
    }

    @DeleteMapping("/{lessonPlanId}")
    @RequiresRole({"TEACHER"})
    @RequiresPermission("LESSON_PLAN_DELETE")
    @Operation(summary = "Delete lesson plan")
    public ResponseEntity<ApiResponse<Void>> deleteLessonPlan(
            @PathVariable Long lessonPlanId) {
        System.out.println("Deleting lesson plan: " + lessonPlanId);
        lessonPlanService.deleteLessonPlan(lessonPlanId);
        return ResponseEntity.ok(ApiResponse.success("Lesson plan deleted successfully", null));
    }
}