package com.sarana_NIS.newTown_backend.controller.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.MeetingActionRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.MeetingSummaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.MeetingAttendeeResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Meeting;
import com.sarana_NIS.newTown_backend.model.enums.MeetingStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.communication.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/communication/meetings")
@Tag(name = "Meeting Management", description = "Meeting management APIs")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @RequiresPermission("MEETING_CREATE")
    @Operation(summary = "Create a new meeting")
    public ResponseEntity<ApiResponse<Meeting>> createMeeting(
            @Valid @RequestBody Meeting meeting) {
        System.out.println("Creating meeting: " + meeting.getTitle());
        Long currentUserId = 1L;
        Meeting created = meetingService.createMeeting(meeting, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Meeting created successfully", created));
    }

    @GetMapping("/{meetingId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @Operation(summary = "Get meeting by ID")
    public ResponseEntity<ApiResponse<Meeting>> getMeetingById(
            @PathVariable Long meetingId) {
        System.out.println("Fetching meeting: " + meetingId);
        Meeting meeting = meetingService.getMeetingById(meetingId);
        return ResponseEntity.ok(ApiResponse.success(meeting));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @Operation(summary = "Get meetings by status")
    public ResponseEntity<ApiResponse<List<Meeting>>> getMeetingsByStatus(
            @PathVariable MeetingStatus status) {
        System.out.println("Fetching meetings by status: " + status);
        List<Meeting> meetings = meetingService.getMeetingsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(meetings));
    }

    @GetMapping("/date/{date}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @Operation(summary = "Get meetings by date")
    public ResponseEntity<ApiResponse<List<Meeting>>> getMeetingsByDate(
            @PathVariable LocalDate date) {
        System.out.println("Fetching meetings on date: " + date);
        List<Meeting> meetings = meetingService.getMeetingsByDate(date);
        return ResponseEntity.ok(ApiResponse.success(meetings));
    }

    @GetMapping("/date-range")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @Operation(summary = "Get meetings by date range")
    public ResponseEntity<ApiResponse<List<Meeting>>> getMeetingsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        System.out.println("Fetching meetings from " + startDate + " to " + endDate);
        List<Meeting> meetings = meetingService.getMeetingsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(meetings));
    }

    @GetMapping("/upcoming")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @Operation(summary = "Get upcoming meetings")
    public ResponseEntity<ApiResponse<List<Meeting>>> getUpcomingMeetings() {
        System.out.println("Fetching upcoming meetings");
        List<Meeting> meetings = meetingService.getUpcomingMeetings();
        return ResponseEntity.ok(ApiResponse.success(meetings));
    }

    @GetMapping("/scheduled/{date}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @Operation(summary = "Get scheduled meetings for date")
    public ResponseEntity<ApiResponse<List<Meeting>>> getScheduledMeetingsForDate(
            @PathVariable LocalDate date) {
        System.out.println("Fetching scheduled meetings for date: " + date);
        List<Meeting> meetings = meetingService.getScheduledMeetingsForDate(date);
        return ResponseEntity.ok(ApiResponse.success(meetings));
    }

    @GetMapping("/{meetingId}/summary")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @Operation(summary = "Get meeting summary")
    public ResponseEntity<ApiResponse<MeetingSummaryResponse>> getMeetingSummary(
            @PathVariable Long meetingId) {
        System.out.println("Fetching meeting summary: " + meetingId);
        MeetingSummaryResponse response = meetingService.getMeetingSummary(meetingId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{meetingId}/attendees")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "TEACHER"})
    @Operation(summary = "Get meeting attendees")
    public ResponseEntity<ApiResponse<MeetingAttendeeResponse>> getMeetingAttendees(
            @PathVariable Long meetingId) {
        System.out.println("Fetching meeting attendees: " + meetingId);
        MeetingAttendeeResponse response = meetingService.getMeetingAttendees(meetingId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/complete")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MEETING_UPDATE")
    @Operation(summary = "Complete meeting")
    public ResponseEntity<ApiResponse<Meeting>> completeMeeting(
            @Valid @RequestBody MeetingActionRequest request) {
        System.out.println("Completing meeting: " + request.getMeetingId());
        Meeting meeting = meetingService.completeMeeting(request.getMeetingId());
        return ResponseEntity.ok(ApiResponse.success("Meeting completed successfully", meeting));
    }

    @PostMapping("/cancel")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MEETING_UPDATE")
    @Operation(summary = "Cancel meeting")
    public ResponseEntity<ApiResponse<Meeting>> cancelMeeting(
            @Valid @RequestBody MeetingActionRequest request) {
        System.out.println("Cancelling meeting: " + request.getMeetingId());
        Meeting meeting = meetingService.cancelMeeting(request.getMeetingId());
        return ResponseEntity.ok(ApiResponse.success("Meeting cancelled successfully", meeting));
    }

    @PostMapping("/reschedule")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MEETING_UPDATE")
    @Operation(summary = "Reschedule meeting")
    public ResponseEntity<ApiResponse<Meeting>> rescheduleMeeting(
            @Valid @RequestBody MeetingActionRequest request) {
        System.out.println("Rescheduling meeting: " + request.getMeetingId());
        Meeting meeting = meetingService.rescheduleMeeting(request);
        return ResponseEntity.ok(ApiResponse.success("Meeting rescheduled successfully", meeting));
    }

    @GetMapping("/stats")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get meeting statistics")
    public ResponseEntity<ApiResponse<Object>> getMeetingStats() {
        System.out.println("Fetching meeting statistics");
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{meetingId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MEETING_DELETE")
    @Operation(summary = "Delete meeting")
    public ResponseEntity<ApiResponse<Void>> deleteMeeting(
            @PathVariable Long meetingId) {
        System.out.println("Deleting meeting: " + meetingId);
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.ok(ApiResponse.success("Meeting deleted successfully", null));
    }
}