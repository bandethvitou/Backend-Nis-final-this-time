package com.sarana_NIS.newTown_backend.controller.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.VisitorRequest;
import com.sarana_NIS.newTown_backend.dto.request.admissions.VisitorCheckoutRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.VisitorResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.VisitorPassResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.admissions.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admissions/visitors")
@Tag(name = "Visitor Management", description = "Visitor registration and tracking APIs")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("VISITOR_CREATE")
    @Operation(summary = "Register a new visitor")
    public ResponseEntity<ApiResponse<VisitorResponse>> registerVisitor(
            @Valid @RequestBody VisitorRequest request) {
        System.out.println("Registering visitor: " + request.getName());
        VisitorResponse response = visitorService.registerVisitor(request);
        return ResponseEntity.ok(ApiResponse.success("Visitor registered successfully", response));
    }

    @PostMapping("/checkout")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("VISITOR_UPDATE")
    @Operation(summary = "Checkout visitor")
    public ResponseEntity<ApiResponse<VisitorResponse>> checkoutVisitor(
            @Valid @RequestBody VisitorCheckoutRequest request) {
        System.out.println("Checking out visitor: " + request.getVisitorId());
        VisitorResponse response = visitorService.checkoutVisitor(request);
        return ResponseEntity.ok(ApiResponse.success("Visitor checked out successfully", response));
    }

    @GetMapping("/{visitorId}")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get visitor by ID")
    public ResponseEntity<ApiResponse<VisitorResponse>> getVisitorById(
            @PathVariable Long visitorId) {
        System.out.println("Fetching visitor: " + visitorId);
        VisitorResponse response = visitorService.getVisitorById(visitorId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/pass/{passNumber}")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get visitor by pass number")
    public ResponseEntity<ApiResponse<VisitorPassResponse>> getVisitorByPassNumber(
            @PathVariable String passNumber) {
        System.out.println("Fetching visitor by pass: " + passNumber);
        VisitorPassResponse response = visitorService.getVisitorByPassNumber(passNumber);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/active")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get active visitors")
    public ResponseEntity<ApiResponse<List<VisitorResponse>>> getActiveVisitors() {
        System.out.println("Fetching active visitors");
        List<VisitorResponse> visitors = visitorService.getActiveVisitors();
        return ResponseEntity.ok(ApiResponse.success(visitors));
    }

    @GetMapping("/host/{hostId}")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get visitors by host")
    public ResponseEntity<ApiResponse<List<VisitorResponse>>> getVisitorsByHost(
            @PathVariable Long hostId) {
        System.out.println("Fetching visitors by host: " + hostId);
        List<VisitorResponse> visitors = visitorService.getVisitorsByHost(hostId);
        return ResponseEntity.ok(ApiResponse.success(visitors));
    }

    @GetMapping("/date-range")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get visitors by date range")
    public ResponseEntity<ApiResponse<List<VisitorResponse>>> getVisitorsByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        System.out.println("Fetching visitors from " + start + " to " + end);
        List<VisitorResponse> visitors = visitorService.getVisitorsByDateRange(start, end);
        return ResponseEntity.ok(ApiResponse.success(visitors));
    }

    @GetMapping("/search")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Search visitors by name")
    public ResponseEntity<ApiResponse<List<VisitorResponse>>> searchVisitors(
            @RequestParam String name) {
        System.out.println("Searching visitors by name: " + name);
        List<VisitorResponse> visitors = visitorService.searchVisitors(name);
        return ResponseEntity.ok(ApiResponse.success(visitors));
    }

    @GetMapping("/no-checkout")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get visitors with no checkout")
    public ResponseEntity<ApiResponse<List<VisitorResponse>>> getVisitorsWithNoCheckout() {
        System.out.println("Fetching visitors with no checkout");
        List<VisitorResponse> visitors = visitorService.findVisitorsWithNoCheckout();
        return ResponseEntity.ok(ApiResponse.success(visitors));
    }

    @GetMapping("/stats")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get visitor statistics")
    public ResponseEntity<ApiResponse<Object>> getVisitorStats() {
        System.out.println("Fetching visitor statistics");
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{visitorId}")
    @RequiresRole({"RECEPTIONIST", "ADMISSIONS_OFFICER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("VISITOR_DELETE")
    @Operation(summary = "Delete visitor")
    public ResponseEntity<ApiResponse<Void>> deleteVisitor(
            @PathVariable Long visitorId) {
        System.out.println("Deleting visitor: " + visitorId);
        visitorService.deleteVisitor(visitorId);
        return ResponseEntity.ok(ApiResponse.success("Visitor deleted successfully", null));
    }
}