package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.CheckinRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.StudentCheckinResponse;
import com.sarana_NIS.newTown_backend.model.entity.admissions.StudentCheckin;
import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.admissions.StudentCheckinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/academic/checkin")
@Tag(name = "Student Check-in Management", description = "Student check-in/out APIs")
public class StudentCheckinController {

    private final StudentCheckinService checkinService;

    public StudentCheckinController(StudentCheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @PostMapping("/checkin")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("CHECKIN_MARK")
    @Operation(summary = "Check in a student")
    public ResponseEntity<ApiResponse<StudentCheckin>> checkIn(
            @Valid @RequestBody CheckinRequest request) {
        System.out.println("Checking in student: " + request.getStudentId());
        Long currentUserId = 1L;
        StudentCheckin checkin = checkinService.checkIn(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Student checked in successfully", checkin));
    }

    @PostMapping("/{checkinId}/checkout")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("CHECKIN_MARK")
    @Operation(summary = "Check out a student")
    public ResponseEntity<ApiResponse<StudentCheckin>> checkOut(
            @PathVariable Long checkinId) {
        System.out.println("Checking out student: " + checkinId);
        StudentCheckin checkin = checkinService.checkOut(checkinId);
        return ResponseEntity.ok(ApiResponse.success("Student checked out successfully", checkin));
    }

    @PostMapping("/bulk")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("CHECKIN_MARK")
    @Operation(summary = "Bulk check in students")
    public ResponseEntity<ApiResponse<List<StudentCheckin>>> bulkCheckIn(
            @Valid @RequestBody CheckinRequest request) {
        int count = request.getBulkCheckins() != null ? request.getBulkCheckins().size() : 0;
        System.out.println("Bulk checking in " + count + " students");
        Long currentUserId = 1L;
        List<StudentCheckin> checkins = checkinService.bulkCheckIn(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Bulk check-in completed", checkins));
    }

    @GetMapping("/{checkinId}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "PARENT"})
    @Operation(summary = "Get check-in by ID")
    public ResponseEntity<ApiResponse<StudentCheckin>> getCheckinById(
            @PathVariable Long checkinId) {
        System.out.println("Fetching check-in: " + checkinId);
        StudentCheckin checkin = checkinService.getCheckinById(checkinId);
        return ResponseEntity.ok(ApiResponse.success(checkin));
    }

    @GetMapping("/student/{studentId}/date/{date}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "PARENT"})
    @Operation(summary = "Get check-in by student and date")
    public ResponseEntity<ApiResponse<StudentCheckin>> getCheckinByStudentAndDate(
            @PathVariable Long studentId,
            @PathVariable LocalDate date) {
        System.out.println("Fetching check-in for student: " + studentId + " on date: " + date);
        StudentCheckin checkin = checkinService.getCheckinByStudentAndDate(studentId, date);
        return ResponseEntity.ok(ApiResponse.success(checkin));
    }

    @GetMapping("/class/{classId}/date/{date}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get check-ins by class and date")
    public ResponseEntity<ApiResponse<List<StudentCheckin>>> getCheckinsByClassAndDate(
            @PathVariable Long classId,
            @PathVariable LocalDate date) {
        System.out.println("Fetching check-ins for class: " + classId + " on date: " + date);
        List<StudentCheckin> checkins = checkinService.getCheckinsByClassAndDate(classId, date);
        return ResponseEntity.ok(ApiResponse.success(checkins));
    }

    @GetMapping("/student/{studentId}/range")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "PARENT"})
    @Operation(summary = "Get check-ins by student and date range")
    public ResponseEntity<ApiResponse<List<StudentCheckin>>> getCheckinsByStudentDateRange(
            @PathVariable Long studentId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        System.out.println("Fetching check-ins for student: " + studentId + " from " + startDate + " to " + endDate);
        List<StudentCheckin> checkins = checkinService.getCheckinsByStudentDateRange(studentId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(checkins));
    }

    @GetMapping("/status/{status}/date/{date}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get check-ins by status and date")
    public ResponseEntity<ApiResponse<List<StudentCheckin>>> getCheckinsByStatusAndDate(
            @PathVariable AttendanceStatus status,
            @PathVariable LocalDate date) {
        System.out.println("Fetching check-ins by status: " + status + " on date: " + date);
        List<StudentCheckin> checkins = checkinService.getCheckinsByStatusAndDate(status, date);
        return ResponseEntity.ok(ApiResponse.success(checkins));
    }

    @GetMapping("/late/{date}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get late check-ins for date")
    public ResponseEntity<ApiResponse<List<StudentCheckin>>> getLateCheckins(
            @PathVariable LocalDate date) {
        System.out.println("Fetching late check-ins for date: " + date);
        List<StudentCheckin> checkins = checkinService.getLateCheckins(date);
        return ResponseEntity.ok(ApiResponse.success(checkins));
    }

    @GetMapping("/early/{date}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get early check-ins for date")
    public ResponseEntity<ApiResponse<List<StudentCheckin>>> getEarlyCheckins(
            @PathVariable LocalDate date) {
        System.out.println("Fetching early check-ins for date: " + date);
        List<StudentCheckin> checkins = checkinService.getEarlyCheckins(date);
        return ResponseEntity.ok(ApiResponse.success(checkins));
    }

    @GetMapping("/stats/class/{classId}/date/{date}/status/{status}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Count check-ins by class, date, and status")
    public ResponseEntity<ApiResponse<Long>> countByClassDateStatus(
            @PathVariable Long classId,
            @PathVariable LocalDate date,
            @PathVariable AttendanceStatus status) {
        long count = checkinService.countByClassDateStatus(classId, date, status);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @GetMapping("/stats/late-average/{date}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get average late minutes for date")
    public ResponseEntity<ApiResponse<Double>> getAverageLateMinutes(
            @PathVariable LocalDate date) {
        double avg = checkinService.getAverageLateMinutes(date);
        return ResponseEntity.ok(ApiResponse.success(avg));
    }

    @DeleteMapping("/{checkinId}")
    @RequiresRole({"TEACHER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("CHECKIN_DELETE")
    @Operation(summary = "Delete check-in record")
    public ResponseEntity<ApiResponse<Void>> deleteCheckin(
            @PathVariable Long checkinId) {
        System.out.println("Deleting check-in record: " + checkinId);
        checkinService.deleteCheckin(checkinId);
        return ResponseEntity.ok(ApiResponse.success("Check-in record deleted successfully", null));
    }
}