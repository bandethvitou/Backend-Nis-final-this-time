package com.sarana_NIS.newTown_backend.dto.response.position;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.ActionType;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionHistoryResponse {

    private Long id;
    private Long staffPositionId;
    private String staffName;
    private String positionName;
    private ActionType action;
    private String changedBy;
    private String changedByName;
    private LocalDateTime changedAt;
    private String notes;
    private String oldValue;
    private String newValue;

    // Constructors
    public PositionHistoryResponse() {}

    public PositionHistoryResponse(Long id, Long staffPositionId, ActionType action) {
        this.id = id;
        this.staffPositionId = staffPositionId;
        this.action = action;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStaffPositionId() { return staffPositionId; }
    public void setStaffPositionId(Long staffPositionId) { this.staffPositionId = staffPositionId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public ActionType getAction() { return action; }
    public void setAction(ActionType action) { this.action = action; }

    public String getChangedBy() { return changedBy; }
    public void setChangedBy(String changedBy) { this.changedBy = changedBy; }

    public String getChangedByName() { return changedByName; }
    public void setChangedByName(String changedByName) { this.changedByName = changedByName; }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getOldValue() { return oldValue; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }

    public String getNewValue() { return newValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }
}