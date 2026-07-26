package com.sarana_NIS.newTown_backend.model.entity.it;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.MaintenanceStatus;
import com.sarana_NIS.newTown_backend.model.enums.MaintenanceType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance")
public class Maintenance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    @Column(name = "maintenance_type", nullable = false)
    private MaintenanceType maintenanceType;

    @Column(columnDefinition = "TEXT")
    private String issue;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MaintenanceStatus status = MaintenanceStatus.SCHEDULED;

    @Column(name = "maintenance_date")
    private LocalDate maintenanceDate;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(name = "completed_date")
    private LocalDateTime completedDate;

    @Column(name = "technician", length = 100)
    private String technician;

    @Column(name = "action_taken", columnDefinition = "TEXT")
    private String actionTaken;

    @Column(name = "cost")
    private Double cost;  // ✅ This is Double, not BigDecimal

    @Column(name = "priority", length = 20)
    private String priority = "MEDIUM";

    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reportedBy;

    // ❌ NO notes field - use description instead

    // Constructors
    public Maintenance() {
        this.status = MaintenanceStatus.SCHEDULED;
        this.priority = "MEDIUM";
    }

    public Maintenance(Equipment equipment, MaintenanceType maintenanceType, String issue) {
        this.equipment = equipment;
        this.maintenanceType = maintenanceType;
        this.issue = issue;
        this.status = MaintenanceStatus.SCHEDULED;
        this.priority = "MEDIUM";
        this.scheduledDate = LocalDate.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    // ❌ NO setNotes() method
    // ❌ NO notes field

    // Helper methods
    public boolean isOverdue() {
        if (status == MaintenanceStatus.COMPLETED || status == MaintenanceStatus.CANCELLED) {
            return false;
        }
        if (maintenanceDate == null) return false;
        return maintenanceDate.isBefore(LocalDate.now());
    }

    public boolean isScheduled() {
        return status == MaintenanceStatus.SCHEDULED;
    }

    public boolean isInProgress() {
        return status == MaintenanceStatus.IN_PROGRESS;
    }

    public boolean isCompleted() {
        return status == MaintenanceStatus.COMPLETED;
    }

    public boolean isCancelled() {
        return status == MaintenanceStatus.CANCELLED;
    }

    public void startMaintenance() {
        this.status = MaintenanceStatus.IN_PROGRESS;
    }

    public void complete(String actionTaken) {
        this.status = MaintenanceStatus.COMPLETED;
        this.completedDate = LocalDateTime.now();
        this.actionTaken = actionTaken;
    }

    public void cancel() {
        this.status = MaintenanceStatus.CANCELLED;
    }

    public void schedule(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
        this.status = MaintenanceStatus.SCHEDULED;
    }

    public long calculateDaysOverdue() {
        if (!isOverdue()) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(maintenanceDate, LocalDate.now());
    }
}