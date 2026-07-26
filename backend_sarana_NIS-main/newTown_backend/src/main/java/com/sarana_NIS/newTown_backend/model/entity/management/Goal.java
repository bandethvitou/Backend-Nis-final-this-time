package com.sarana_NIS.newTown_backend.model.entity.management;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.GoalStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal extends BaseEntity {  // ✅ Inherits id from BaseEntity

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal target;

    private BigDecimal current;

    private String unit;

    @Column(nullable = false)
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Enumerated(EnumType.STRING)
    private GoalStatus status = GoalStatus.NOT_STARTED;

    // Constructors
    public Goal() {}

    public Goal(String title, BigDecimal target, LocalDate deadline) {
        this.title = title;
        this.target = target;
        this.deadline = deadline;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getTarget() { return target; }
    public void setTarget(BigDecimal target) { this.target = target; }

    public BigDecimal getCurrent() { return current; }
    public void setCurrent(BigDecimal current) { this.current = current; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public User getManager() { return manager; }
    public void setManager(User manager) { this.manager = manager; }

    public GoalStatus getStatus() { return status; }
    public void setStatus(GoalStatus status) { this.status = status; }
}