package com.sarana_NIS.newTown_backend.controller.core;

import com.sarana_NIS.newTown_backend.dto.request.core.SettingsUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.SettingsResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.core.SchoolSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@Tag(name = "System Settings", description = "School settings management APIs")
public class SettingsController {

    private final SchoolSettingService settingsService;

    public SettingsController(SchoolSettingService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping
    @RequiresRole({"DIRECTOR"})
    @Operation(summary = "Get school settings")
    public ResponseEntity<ApiResponse<SettingsResponse>> getSettings() {
        System.out.println("Fetching settings");
        SettingsResponse response = settingsService.getSettings();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/school-name")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get school name")
    public ResponseEntity<ApiResponse<String>> getSchoolName() {
        System.out.println("Fetching school name");
        String name = settingsService.getSchoolName();
        return ResponseEntity.ok(ApiResponse.success(name));
    }

    @GetMapping("/academic-year")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get current academic year")
    public ResponseEntity<ApiResponse<String>> getCurrentAcademicYear() {
        System.out.println("Fetching academic year");
        String year = settingsService.getCurrentAcademicYear();
        return ResponseEntity.ok(ApiResponse.success(year));
    }

    @GetMapping("/term")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get current term")
    public ResponseEntity<ApiResponse<String>> getCurrentTerm() {
        System.out.println("Fetching current term");
        String term = settingsService.getCurrentTerm();
        return ResponseEntity.ok(ApiResponse.success(term));
    }

    @PutMapping
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("SETTINGS_UPDATE")
    @Operation(summary = "Update school settings")
    public ResponseEntity<ApiResponse<SettingsResponse>> updateSettings(
            @Valid @RequestBody SettingsUpdateRequest request) {
        System.out.println("Updating settings");
        SettingsResponse response = settingsService.updateSettings(request);
        return ResponseEntity.ok(ApiResponse.success("Settings updated successfully", response));
    }

    @PatchMapping("/logo")
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("SETTINGS_UPDATE")
    @Operation(summary = "Update school logo")
    public ResponseEntity<ApiResponse<Void>> updateLogo(
            @RequestParam Long id,
            @RequestParam String logoUrl) {
        System.out.println("Updating logo");
        settingsService.updateLogo(id, logoUrl);
        return ResponseEntity.ok(ApiResponse.success("Logo updated successfully", null));
    }

    @PatchMapping("/academic-year")
    @RequiresRole({"DIRECTOR"})
    @RequiresPermission("SETTINGS_UPDATE")
    @Operation(summary = "Update academic year")
    public ResponseEntity<ApiResponse<Void>> updateAcademicYear(
            @RequestParam Long id,
            @RequestParam String academicYear) {
        System.out.println("Updating academic year: " + academicYear);
        settingsService.updateAcademicYear(id, academicYear);
        return ResponseEntity.ok(ApiResponse.success("Academic year updated successfully", null));
    }
}