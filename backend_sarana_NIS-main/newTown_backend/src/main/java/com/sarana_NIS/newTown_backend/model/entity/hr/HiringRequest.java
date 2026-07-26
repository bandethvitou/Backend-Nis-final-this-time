package com.sarana_NIS.newTown_backend.model.entity.hr;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.RequestStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "hiring_requests")
public class HiringRequest extends BaseEntity {  // ✅ Inherits id from BaseEntity

    @Column(nullable = false, length = 100)
    private String position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "number_of_positions")
    private Integer numberOfPositions = 1;

    @Column(columnDefinition = "TEXT")
    private String justification;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "requested_by")
    private User requestedBy;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    // Constructors
    public HiringRequest() {}

    public HiringRequest(String position, Department department) {
        this.position = position;
        this.department = department;
    }

    // Getters and Setters
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Integer getNumberOfPositions() { return numberOfPositions; }
    public void setNumberOfPositions(Integer numberOfPositions) { this.numberOfPositions = numberOfPositions; }

    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public User getRequestedBy() { return requestedBy; }
    public void setRequestedBy(User requestedBy) { this.requestedBy = requestedBy; }

    public User getApprovedBy() { return approvedBy; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }
}