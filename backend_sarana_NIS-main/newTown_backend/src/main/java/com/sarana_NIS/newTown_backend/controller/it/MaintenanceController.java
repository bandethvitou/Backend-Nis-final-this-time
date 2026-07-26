package com.sarana_NIS.newTown_backend.controller.it;

import com.sarana_NIS.newTown_backend.dto.request.it.MaintenanceStatusRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.it.MaintenanceScheduleResponse;
import com.sarana_NIS.newTown_backend.model.entity.it.Maintenance;
import com.sarana_NIS.newTown_backend.model.enums.MaintenanceStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.it.MaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/it/maintenance")
@Tag(name = "IT Maintenance Management", description = "IT maintenance management APIs")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("MAINTENANCE_CREATE")
    @Operation(summary = "Create maintenance record")
    public ResponseEntity<ApiResponse<Maintenance>> createMaintenance(
            @Valid @RequestBody Maintenance maintenance) {
        System.out.println("Creating maintenance record for equipment: " + maintenance.getEquipment().getId());
        Maintenance created = maintenanceService.createMaintenance(maintenance);
        return ResponseEntity.ok(ApiResponse.success("Maintenance record created successfully", created));
    }

    @GetMapping("/{maintenanceId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get maintenance by ID")
    public ResponseEntity<ApiResponse<Maintenance>> getMaintenanceById(
            @PathVariable Long maintenanceId) {
        System.out.println("Fetching maintenance: " + maintenanceId);
        Maintenance maintenance = maintenanceService.getMaintenanceById(maintenanceId);
        return ResponseEntity.ok(ApiResponse.success(maintenance));
    }

    @GetMapping("/equipment/{equipmentId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get maintenance by equipment")
    public ResponseEntity<ApiResponse<List<Maintenance>>> getMaintenanceByEquipment(
            @PathVariable Long equipmentId) {
        System.out.println("Fetching maintenance for equipment: " + equipmentId);
        List<Maintenance> maintenance = maintenanceService.getMaintenanceByEquipment(equipmentId);
        return ResponseEntity.ok(ApiResponse.success(maintenance));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get maintenance by status")
    public ResponseEntity<ApiResponse<List<Maintenance>>> getMaintenanceByStatus(
            @PathVariable MaintenanceStatus status) {
        System.out.println("Fetching maintenance by status: " + status);
        List<Maintenance> maintenance = maintenanceService.getMaintenanceByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(maintenance));
    }

    @GetMapping("/active")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get active maintenance")
    public ResponseEntity<ApiResponse<List<Maintenance>>> getActiveMaintenance() {
        System.out.println("Fetching active maintenance");
        List<Maintenance> maintenance = maintenanceService.getActiveMaintenance();
        return ResponseEntity.ok(ApiResponse.success(maintenance));
    }

    @GetMapping("/schedule")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get maintenance schedule")
    public ResponseEntity<ApiResponse<MaintenanceScheduleResponse>> getMaintenanceSchedule() {
        System.out.println("Fetching maintenance schedule");
        MaintenanceScheduleResponse response = maintenanceService.getMaintenanceSchedule();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/overdue")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get overdue maintenance")
    public ResponseEntity<ApiResponse<List<Maintenance>>> getOverdueMaintenance() {
        System.out.println("Fetching overdue maintenance");
        List<Maintenance> maintenance = maintenanceService.getOverdueMaintenance();
        return ResponseEntity.ok(ApiResponse.success(maintenance));
    }

    @GetMapping("/date-range")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get maintenance by date range")
    public ResponseEntity<ApiResponse<List<Maintenance>>> getMaintenanceByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        System.out.println("Fetching maintenance from " + startDate + " to " + endDate);
        List<Maintenance> maintenance = maintenanceService.getMaintenanceByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(maintenance));
    }

    @PutMapping("/status")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("MAINTENANCE_UPDATE")
    @Operation(summary = "Update maintenance status")
    public ResponseEntity<ApiResponse<Maintenance>> updateMaintenanceStatus(
            @Valid @RequestBody MaintenanceStatusRequest request) {
        System.out.println("Updating maintenance status: " + request.getMaintenanceId());
        Maintenance maintenance = maintenanceService.updateMaintenanceStatus(request);
        return ResponseEntity.ok(ApiResponse.success("Maintenance status updated successfully", maintenance));
    }

    @PatchMapping("/{maintenanceId}/in-progress")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("MAINTENANCE_UPDATE")
    @Operation(summary = "Mark maintenance as in progress")
    public ResponseEntity<ApiResponse<Maintenance>> markInProgress(
            @PathVariable Long maintenanceId) {
        System.out.println("Marking maintenance as in progress: " + maintenanceId);
        Maintenance maintenance = maintenanceService.markInProgress(maintenanceId);
        return ResponseEntity.ok(ApiResponse.success("Maintenance marked as in progress", maintenance));
    }

    @PostMapping("/{maintenanceId}/complete")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("MAINTENANCE_COMPLETE")
    @Operation(summary = "Mark maintenance as completed")
    public ResponseEntity<ApiResponse<Maintenance>> markCompleted(
            @PathVariable Long maintenanceId,
            @RequestParam String actionTaken,
            @RequestParam(required = false) Double cost) {
        System.out.println("Marking maintenance as completed: " + maintenanceId);
        Maintenance maintenance = maintenanceService.markCompleted(maintenanceId, actionTaken, cost);
        return ResponseEntity.ok(ApiResponse.success("Maintenance marked as completed", maintenance));
    }

    @GetMapping("/stats")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get maintenance statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMaintenanceStatistics() {
        System.out.println("Fetching maintenance statistics");
        Map<String, Object> stats = maintenanceService.getMaintenanceStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @DeleteMapping("/{maintenanceId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("MAINTENANCE_DELETE")
    @Operation(summary = "Delete maintenance record")
    public ResponseEntity<ApiResponse<Void>> deleteMaintenance(
            @PathVariable Long maintenanceId) {
        System.out.println("Deleting maintenance record: " + maintenanceId);
        maintenanceService.deleteMaintenance(maintenanceId);
        return ResponseEntity.ok(ApiResponse.success("Maintenance record deleted successfully", null));
    }
}