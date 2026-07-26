package com.sarana_NIS.newTown_backend.dto.request.it;

import jakarta.validation.constraints.NotNull;

public class MaintenanceStatusRequest {

    @NotNull(message = "Maintenance ID is required")
    private Long maintenanceId;

    @NotNull(message = "Status is required")
    private String status;

    private String actionTaken;
    private String technician;
    private Double cost;  // ✅ Use Double, not BigDecimal
    private String notes;

    // Constructors
    public MaintenanceStatusRequest() {}

    public MaintenanceStatusRequest(Long maintenanceId, String status) {
        this.maintenanceId = maintenanceId;
        this.status = status;
    }

    // Getters and Setters
    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}