package com.sarana_NIS.newTown_backend.dto.request.user;

import com.sarana_NIS.newTown_backend.model.enums.Shift;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class StaffCreateRequest {

    @NotBlank(message = "Staff ID is required")
    private String staffId;

    private Long userId;
    private Long departmentId;
    private BigDecimal baseSalary;
    private BigDecimal allowances;
    private Shift shift;
    private LocalDate joinDate;
    private List<Long> positionIds;
    private List<TempPositionRequest> tempPositions;

    // Constructors
    public StaffCreateRequest() {
    }

    // Getters and Setters
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getAllowances() {
        return allowances;
    }

    public void setAllowances(BigDecimal allowances) {
        this.allowances = allowances;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public List<Long> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Long> positionIds) {
        this.positionIds = positionIds;
    }

    public List<TempPositionRequest> getTempPositions() {
        return tempPositions;
    }

    public void setTempPositions(List<TempPositionRequest> tempPositions) {
        this.tempPositions = tempPositions;
    }

    // Inner class for temporary positions
    public static class TempPositionRequest {
        private Long positionId;
        private LocalDate startDate;
        private LocalDate endDate;

        public TempPositionRequest() {
        }

        public Long getPositionId() {
            return positionId;
        }

        public void setPositionId(Long positionId) {
            this.positionId = positionId;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }
    }
}