package com.sarana_NIS.newTown_backend.dto.request.it;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EquipmentAssignmentRequest {

    @NotNull(message = "Equipment ID is required")
    private Long equipmentId;

    @NotNull(message = "Assigned to user ID is required")
    private Long assignedToUserId;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    private String location;
    private String purpose;

    // Constructors
    public EquipmentAssignmentRequest() {}

    public EquipmentAssignmentRequest(Long equipmentId, Long assignedToUserId) {
        this.equipmentId = equipmentId;
        this.assignedToUserId = assignedToUserId;
    }

    // Getters and Setters
    public Long getEquipmentId() { return equipmentId; }
    public void setEquipmentId(Long equipmentId) { this.equipmentId = equipmentId; }

    public Long getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(Long assignedToUserId) { this.assignedToUserId = assignedToUserId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}