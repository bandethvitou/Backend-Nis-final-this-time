package com.sarana_NIS.newTown_backend.controller.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.TourRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.TourResponse;
import com.sarana_NIS.newTown_backend.model.enums.TourStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.admissions.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admissions/tours")
@Tag(name = "Tour Management", description = "Campus tour management APIs")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TOUR_CREATE")
    @Operation(summary = "Create a new tour")
    public ResponseEntity<ApiResponse<TourResponse>> createTour(
            @Valid @RequestBody TourRequest request) {
        System.out.println("Creating tour for parent: " + request.getParentName());
        TourResponse response = tourService.createTour(request);
        return ResponseEntity.ok(ApiResponse.success("Tour created successfully", response));
    }

    @GetMapping("/{tourId}")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get tour by ID")
    public ResponseEntity<ApiResponse<TourResponse>> getTourById(
            @PathVariable Long tourId) {
        System.out.println("Fetching tour: " + tourId);
        TourResponse response = tourService.getTourById(tourId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get tours by status")
    public ResponseEntity<ApiResponse<List<TourResponse>>> getToursByStatus(
            @PathVariable TourStatus status) {
        System.out.println("Fetching tours by status: " + status);
        List<TourResponse> tours = tourService.getToursByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(tours));
    }

    @GetMapping("/date/{date}")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get tours by date")
    public ResponseEntity<ApiResponse<List<TourResponse>>> getToursByDate(
            @PathVariable LocalDate date) {
        System.out.println("Fetching tours on date: " + date);
        List<TourResponse> tours = tourService.getToursByDate(date);
        return ResponseEntity.ok(ApiResponse.success(tours));
    }

    @GetMapping("/date-range")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get tours by date range")
    public ResponseEntity<ApiResponse<List<TourResponse>>> getToursByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        System.out.println("Fetching tours from " + startDate + " to " + endDate);
        List<TourResponse> tours = tourService.getToursByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(tours));
    }

    @GetMapping("/scheduled/{date}")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get scheduled tours for date")
    public ResponseEntity<ApiResponse<List<TourResponse>>> getScheduledToursForDate(
            @PathVariable LocalDate date) {
        System.out.println("Fetching scheduled tours for date: " + date);
        List<TourResponse> tours = tourService.getScheduledToursForDate(date);
        return ResponseEntity.ok(ApiResponse.success(tours));
    }

    @GetMapping("/upcoming")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get upcoming tours")
    public ResponseEntity<ApiResponse<List<TourResponse>>> getUpcomingTours() {
        System.out.println("Fetching upcoming tours");
        List<TourResponse> tours = tourService.getUpcomingTours();
        return ResponseEntity.ok(ApiResponse.success(tours));
    }

    @GetMapping("/search")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Search tours by name")
    public ResponseEntity<ApiResponse<List<TourResponse>>> searchTours(
            @RequestParam String name) {
        System.out.println("Searching tours by name: " + name);
        List<TourResponse> tours = tourService.searchTours(name);
        return ResponseEntity.ok(ApiResponse.success(tours));
    }

    @PatchMapping("/{tourId}/status")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TOUR_UPDATE")
    @Operation(summary = "Update tour status")
    public ResponseEntity<ApiResponse<TourResponse>> updateTourStatus(
            @PathVariable Long tourId,
            @RequestParam TourStatus status) {
        System.out.println("Updating tour status: " + tourId + " -> " + status);
        TourResponse response = tourService.updateTourStatus(tourId, status);
        return ResponseEntity.ok(ApiResponse.success("Tour status updated successfully", response));
    }

    @PostMapping("/{tourId}/complete")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TOUR_UPDATE")
    @Operation(summary = "Mark tour as completed")
    public ResponseEntity<ApiResponse<TourResponse>> completeTour(
            @PathVariable Long tourId) {
        System.out.println("Marking tour as completed: " + tourId);
        TourResponse response = tourService.completeTour(tourId);
        return ResponseEntity.ok(ApiResponse.success("Tour marked as completed", response));
    }

    @PostMapping("/{tourId}/cancel")
    @RequiresRole({"ADMISSIONS_OFFICER", "RECEPTIONIST", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TOUR_UPDATE")
    @Operation(summary = "Cancel tour")
    public ResponseEntity<ApiResponse<TourResponse>> cancelTour(
            @PathVariable Long tourId) {
        System.out.println("Cancelling tour: " + tourId);
        TourResponse response = tourService.cancelTour(tourId);
        return ResponseEntity.ok(ApiResponse.success("Tour cancelled successfully", response));
    }

    @GetMapping("/stats")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get tour statistics")
    public ResponseEntity<ApiResponse<Object>> getTourStats() {
        System.out.println("Fetching tour statistics");
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{tourId}")
    @RequiresRole({"ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TOUR_DELETE")
    @Operation(summary = "Delete tour")
    public ResponseEntity<ApiResponse<Void>> deleteTour(
            @PathVariable Long tourId) {
        System.out.println("Deleting tour: " + tourId);
        tourService.deleteTour(tourId);
        return ResponseEntity.ok(ApiResponse.success("Tour deleted successfully", null));
    }
}