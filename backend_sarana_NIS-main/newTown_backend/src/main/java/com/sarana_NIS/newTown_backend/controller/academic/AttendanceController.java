package com.sarana_NIS.newTown_backend.controller.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.AttendanceRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.academic.AttendanceSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.Attendance;
import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.academic.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/academic/attendance")
@Tag(name = "Attendance Management", description = "Attendance tracking APIs")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("ATTENDANCE_MARK")
    @Operation(summary = "Mark attendance for a student")
    public ResponseEntity<ApiResponse<Attendance>> markAttendance(
            @Valid @RequestBody AttendanceRequest request) {
        System.out.println("Marking attendance for student: " + request.getStudentId());
        Long currentUserId = 1L;
        Attendance attendance = attendanceService.markAttendance(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Attendance marked successfully", attendance));
    }

    @PostMapping("/bulk")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("ATTENDANCE_MARK")
    @Operation(summary = "Mark bulk attendance")
    public ResponseEntity<ApiResponse<List<Attendance>>> markBulkAttendance(
            @Valid @RequestBody AttendanceRequest request) {
        int count = request.getAttendances() != null ? request.getAttendances().size() : 0;
        System.out.println("Marking bulk attendance for " + count + " students");
        Long currentUserId = 1L;
        List<Attendance> attendances = attendanceService.markBulkAttendance(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Bulk attendance marked successfully", attendances));
    }

    @GetMapping("/{attendanceId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER", "PARENT"})
    @Operation(summary = "Get attendance by ID")
    public ResponseEntity<ApiResponse<Attendance>> getAttendanceById(
            @PathVariable Long attendanceId) {
        System.out.println("Fetching attendance: " + attendanceId);
        Attendance attendance = attendanceService.getAttendanceById(attendanceId);
        return ResponseEntity.ok(ApiResponse.success(attendance));
    }

    @GetMapping("/student/{studentId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER", "PARENT", "STUDENT"})
    @Operation(summary = "Get attendance by student")
    public ResponseEntity<ApiResponse<List<Attendance>>> getAttendanceByStudent(
            @PathVariable Long studentId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        System.out.println("Fetching attendance for student: " + studentId + " from " + startDate + " to " + endDate);
        List<Attendance> attendances = attendanceService.getAttendanceByStudent(studentId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(attendances));
    }

    @GetMapping("/class-subject/{classSubjectId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get attendance by class subject")
    public ResponseEntity<ApiResponse<List<Attendance>>> getAttendanceByClassSubject(
            @PathVariable Long classSubjectId,
            @RequestParam LocalDate date) {
        System.out.println("Fetching attendance for class subject: " + classSubjectId + " on date: " + date);
        List<Attendance> attendances = attendanceService.getAttendanceByClassSubject(classSubjectId, date);
        return ResponseEntity.ok(ApiResponse.success(attendances));
    }

    @GetMapping("/summary/{classSubjectId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Get attendance summary")
    public ResponseEntity<ApiResponse<AttendanceSummaryResponse>> getAttendanceSummary(
            @PathVariable Long classSubjectId,
            @RequestParam LocalDate date) {
        System.out.println("Fetching attendance summary for class subject: " + classSubjectId + " on date: " + date);
        AttendanceSummaryResponse summary = attendanceService.getAttendanceSummary(classSubjectId, date);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PutMapping("/{attendanceId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("ATTENDANCE_UPDATE")
    @Operation(summary = "Update attendance")
    public ResponseEntity<ApiResponse<Attendance>> updateAttendance(
            @PathVariable Long attendanceId,
            @RequestParam String status,
            @RequestParam(required = false) String comment) {
        System.out.println("Updating attendance: " + attendanceId);
        AttendanceStatus attendanceStatus = AttendanceStatus.valueOf(status);
        Attendance attendance = attendanceService.updateAttendance(attendanceId, attendanceStatus, comment);
        return ResponseEntity.ok(ApiResponse.success("Attendance updated successfully", attendance));
    }

    @DeleteMapping("/{attendanceId}")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @RequiresPermission("ATTENDANCE_DELETE")
    @Operation(summary = "Delete attendance")
    public ResponseEntity<ApiResponse<Void>> deleteAttendance(
            @PathVariable Long attendanceId) {
        System.out.println("Deleting attendance: " + attendanceId);
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.ok(ApiResponse.success("Attendance deleted successfully", null));
    }

    @GetMapping("/exists")
    @RequiresRole({"TEACHER", "MANAGER", "TOP_MANAGER"})
    @Operation(summary = "Check if attendance exists")
    public ResponseEntity<ApiResponse<Boolean>> hasAttendance(
            @RequestParam Long studentId,
            @RequestParam Long classSubjectId,
            @RequestParam LocalDate date) {
        boolean exists = attendanceService.hasAttendance(studentId, classSubjectId, date);
        return ResponseEntity.ok(ApiResponse.success(exists));
    }
}