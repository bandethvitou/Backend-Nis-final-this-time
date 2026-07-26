package com.sarana_NIS.newTown_backend.controller.it;

import com.sarana_NIS.newTown_backend.dto.request.it.EquipmentAssignmentRequest;
import com.sarana_NIS.newTown_backend.dto.request.it.EquipmentSearchRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.it.EquipmentStatusResponse;
import com.sarana_NIS.newTown_backend.model.entity.it.Equipment;
import com.sarana_NIS.newTown_backend.model.enums.EquipmentStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.it.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/it/equipment")
@Tag(name = "IT Equipment Management", description = "IT equipment management APIs")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("EQUIPMENT_CREATE")
    @Operation(summary = "Create new equipment")
    public ResponseEntity<ApiResponse<Equipment>> createEquipment(
            @Valid @RequestBody Equipment equipment) {
        System.out.println("Creating new equipment: " + equipment.getName());
        Equipment created = equipmentService.createEquipment(equipment);
        return ResponseEntity.ok(ApiResponse.success("Equipment created successfully", created));
    }

    @GetMapping("/{equipmentId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment by ID")
    public ResponseEntity<ApiResponse<Equipment>> getEquipmentById(
            @PathVariable Long equipmentId) {
        System.out.println("Fetching equipment: " + equipmentId);
        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/serial/{serialNumber}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment by serial number")
    public ResponseEntity<ApiResponse<Equipment>> getEquipmentBySerialNumber(
            @PathVariable String serialNumber) {
        System.out.println("Fetching equipment by serial: " + serialNumber);
        Equipment equipment = equipmentService.getEquipmentBySerialNumber(serialNumber);
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment by status")
    public ResponseEntity<ApiResponse<List<Equipment>>> getEquipmentByStatus(
            @PathVariable EquipmentStatus status) {
        System.out.println("Fetching equipment by status: " + status);
        List<Equipment> equipment = equipmentService.getEquipmentByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/functional")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get functional equipment")
    public ResponseEntity<ApiResponse<List<Equipment>>> getFunctionalEquipment() {
        System.out.println("Fetching functional equipment");
        List<Equipment> equipment = equipmentService.getFunctionalEquipment();
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/under-repair")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment under repair")
    public ResponseEntity<ApiResponse<List<Equipment>>> getUnderRepairEquipment() {
        System.out.println("Fetching equipment under repair");
        List<Equipment> equipment = equipmentService.getUnderRepairEquipment();
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/category/{category}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment by category")
    public ResponseEntity<ApiResponse<List<Equipment>>> getEquipmentByCategory(
            @PathVariable String category) {
        System.out.println("Fetching equipment by category: " + category);
        List<Equipment> equipment = equipmentService.getEquipmentByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/location/{location}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment by location")
    public ResponseEntity<ApiResponse<List<Equipment>>> getEquipmentByLocation(
            @PathVariable String location) {
        System.out.println("Fetching equipment by location: " + location);
        List<Equipment> equipment = equipmentService.getEquipmentByLocation(location);
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/warranty/expired")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment with expired warranty")
    public ResponseEntity<ApiResponse<List<Equipment>>> getExpiredWarranty() {
        System.out.println("Fetching equipment with expired warranty");
        List<Equipment> equipment = equipmentService.getExpiredWarranty();
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/warranty/expiring-soon")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment with warranty expiring soon")
    public ResponseEntity<ApiResponse<List<Equipment>>> getWarrantyExpiringSoon() {
        System.out.println("Fetching equipment with warranty expiring soon");
        List<Equipment> equipment = equipmentService.getWarrantyExpiringSoon();
        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @PostMapping("/search")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Search equipment")
    public ResponseEntity<ApiResponse<PageResponse<Equipment>>> searchEquipment(
            @Valid @RequestBody EquipmentSearchRequest request) {
        System.out.println("Searching equipment with keyword: " + request.getKeyword());
        PageResponse<Equipment> response = equipmentService.searchEquipment(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{equipmentId}/status")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment status details")
    public ResponseEntity<ApiResponse<EquipmentStatusResponse>> getEquipmentStatus(
            @PathVariable Long equipmentId) {
        System.out.println("Fetching equipment status: " + equipmentId);
        EquipmentStatusResponse response = equipmentService.getEquipmentStatus(equipmentId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{equipmentId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("EQUIPMENT_UPDATE")
    @Operation(summary = "Update equipment")
    public ResponseEntity<ApiResponse<Equipment>> updateEquipment(
            @PathVariable Long equipmentId,
            @Valid @RequestBody Equipment equipment) {
        System.out.println("Updating equipment: " + equipmentId);
        Equipment updated = equipmentService.updateEquipment(equipmentId, equipment);
        return ResponseEntity.ok(ApiResponse.success("Equipment updated successfully", updated));
    }

    @PatchMapping("/{equipmentId}/status")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("EQUIPMENT_UPDATE")
    @Operation(summary = "Update equipment status")
    public ResponseEntity<ApiResponse<Equipment>> updateEquipmentStatus(
            @PathVariable Long equipmentId,
            @RequestParam EquipmentStatus status) {
        System.out.println("Updating equipment status: " + equipmentId + " -> " + status);
        Equipment updated = equipmentService.updateEquipmentStatus(equipmentId, status);
        return ResponseEntity.ok(ApiResponse.success("Equipment status updated successfully", updated));
    }

    @PostMapping("/assign")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("EQUIPMENT_ASSIGN")
    @Operation(summary = "Assign equipment to user")
    public ResponseEntity<ApiResponse<Equipment>> assignEquipment(
            @Valid @RequestBody EquipmentAssignmentRequest request) {
        System.out.println("Assigning equipment: " + request.getEquipmentId() + " to user: " + request.getAssignedToUserId());
        Equipment equipment = equipmentService.assignEquipment(request);
        return ResponseEntity.ok(ApiResponse.success("Equipment assigned successfully", equipment));
    }

    @GetMapping("/stats")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get equipment statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getEquipmentStatistics() {
        System.out.println("Fetching equipment statistics");
        Map<String, Object> stats = equipmentService.getEquipmentStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @DeleteMapping("/{equipmentId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("EQUIPMENT_DELETE")
    @Operation(summary = "Delete equipment")
    public ResponseEntity<ApiResponse<Void>> deleteEquipment(
            @PathVariable Long equipmentId) {
        System.out.println("Deleting equipment: " + equipmentId);
        equipmentService.deleteEquipment(equipmentId);
        return ResponseEntity.ok(ApiResponse.success("Equipment deleted successfully", null));
    }
}