package com.sarana_NIS.newTown_backend.model.entity.it;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.TicketCategory;
import com.sarana_NIS.newTown_backend.model.enums.TicketStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private TicketCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reportedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;

    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;

    @Column(name = "resolved_date")
    private LocalDateTime resolvedDate;

    @Column(name = "closed_date")
    private LocalDateTime closedDate;

    @Column(name = "is_urgent")
    private Boolean isUrgent;

    // Constructors
    public Ticket() {
        // Initialize in constructor instead of field declaration
        this.status = TicketStatus.OPEN;
        this.priority = Priority.MEDIUM;
        this.isUrgent = false;
    }

    public Ticket(String title, TicketCategory category) {
        this.title = title;
        this.category = category;
        this.status = TicketStatus.OPEN;
        this.priority = Priority.MEDIUM;
        this.isUrgent = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public void setResolutionNotes(String resolutionNotes) {
        this.resolutionNotes = resolutionNotes;
    }

    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    // Helper methods
    public boolean isOpen() {
        return status == TicketStatus.OPEN;
    }

    public boolean isInProgress() {
        return status == TicketStatus.IN_PROGRESS;
    }

    public boolean isResolved() {
        return status == TicketStatus.RESOLVED;
    }

    public boolean isClosed() {
        return status == TicketStatus.CLOSED;
    }

    public boolean isUrgent() {
        return isUrgent != null && isUrgent;
    }

    public void assignTo(User user) {
        this.assignedTo = user;
        this.assignedDate = LocalDateTime.now();
        this.status = TicketStatus.IN_PROGRESS;
    }

    public void resolve(String resolutionNotes) {
        this.status = TicketStatus.RESOLVED;
        this.resolutionNotes = resolutionNotes;
        this.resolvedDate = LocalDateTime.now();
    }

    public void close() {
        this.status = TicketStatus.CLOSED;
        this.closedDate = LocalDateTime.now();
    }

    public void reopen() {
        this.status = TicketStatus.OPEN;
        this.resolvedDate = null;
        this.closedDate = null;
    }

    public void markUrgent() {
        this.isUrgent = true;
        this.priority = Priority.HIGH;
    }

    public long calculateResolutionTime() {
        // Use getCreatedAt() method instead of direct access
        if (getCreatedAt() == null || resolvedDate == null) return 0;
        return java.time.Duration.between(getCreatedAt(), resolvedDate).toMinutes();
    }

    public boolean isOverdue() {
        if (status == TicketStatus.CLOSED || status == TicketStatus.RESOLVED) {
            return false;
        }
        // Overdue if ticket has been open for more than 3 days
        // Use getCreatedAt() method instead of direct access
        if (getCreatedAt() == null) return false;
        return java.time.Duration.between(getCreatedAt(), LocalDateTime.now()).toDays() > 3;
    }
}