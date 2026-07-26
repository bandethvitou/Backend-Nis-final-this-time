package com.sarana_NIS.newTown_backend.model.entity.position;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.ActionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "position_history")
public class PositionHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "staff_position_id", nullable = false)
    private StaffPosition staffPosition;

    @Enumerated(EnumType.STRING)
    private ActionType action;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Constructors
    public PositionHistory() {}

    public PositionHistory(StaffPosition staffPosition, ActionType action) {
        this.staffPosition = staffPosition;
        this.action = action;
    }

    // Getters and Setters
    public StaffPosition getStaffPosition() { return staffPosition; }
    public void setStaffPosition(StaffPosition staffPosition) { this.staffPosition = staffPosition; }

    public ActionType getAction() { return action; }
    public void setAction(ActionType action) { this.action = action; }

    public User getChangedBy() { return changedBy; }
    public void setChangedBy(User changedBy) { this.changedBy = changedBy; }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}