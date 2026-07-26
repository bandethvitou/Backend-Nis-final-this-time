package com.sarana_NIS.newTown_backend.dto.request.user;

import com.sarana_NIS.newTown_backend.model.enums.EmployeeStatus;
import com.sarana_NIS.newTown_backend.model.enums.Shift;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StaffUpdateRequest {

    private Long departmentId;
    private BigDecimal baseSalary;
    private BigDecimal allowances;
    private Shift shift;
    private LocalDate joinDate;
    private EmployeeStatus staffStatus;

    // Constructors
    public StaffUpdateRequest() {
    }

    // Getters and Setters
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

    public EmployeeStatus getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(EmployeeStatus staffStatus) {
        this.staffStatus = staffStatus;
    }
}