package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.EmployeeStatus;
import com.sarana_NIS.newTown_backend.model.enums.Shift;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffResponse {

    private Long id;
    private String staffId;
    private UserResponse user;
    private DepartmentInfo department;
    private BigDecimal baseSalary;
    private BigDecimal allowances;
    private BigDecimal totalSalary;
    private Shift shift;
    private String shiftDisplay;
    private LocalDate joinDate;
    private EmployeeStatus staffStatus;
    private List<PositionInfo> positions;

    // ✅ ADD THESE MISSING FIELDS
    private Integer leaveDaysUsed;
    private Integer leaveDaysRemaining;
    private Integer leaveDaysTotal;

    // Constructors
    public StaffResponse() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public DepartmentInfo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentInfo department) {
        this.department = department;
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

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getShiftDisplay() {
        return shiftDisplay;
    }

    public void setShiftDisplay(String shiftDisplay) {
        this.shiftDisplay = shiftDisplay;
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

    public List<PositionInfo> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionInfo> positions) {
        this.positions = positions;
    }

    // ✅ ADD GETTERS AND SETTERS FOR NEW FIELDS
    public Integer getLeaveDaysUsed() {
        return leaveDaysUsed;
    }

    public void setLeaveDaysUsed(Integer leaveDaysUsed) {
        this.leaveDaysUsed = leaveDaysUsed;
    }

    public Integer getLeaveDaysRemaining() {
        return leaveDaysRemaining;
    }

    public void setLeaveDaysRemaining(Integer leaveDaysRemaining) {
        this.leaveDaysRemaining = leaveDaysRemaining;
    }

    public Integer getLeaveDaysTotal() {
        return leaveDaysTotal;
    }

    public void setLeaveDaysTotal(Integer leaveDaysTotal) {
        this.leaveDaysTotal = leaveDaysTotal;
    }

    // Inner class for DepartmentInfo
    public static class DepartmentInfo {
        private Long id;
        private String name;
        private String headName;

        public DepartmentInfo() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadName() {
            return headName;
        }

        public void setHeadName(String headName) {
            this.headName = headName;
        }
    }

    // Inner class for PositionInfo
    public static class PositionInfo {
        private Long id;
        private String name;
        private Boolean isTemporary;
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean isActive;

        public PositionInfo() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getIsTemporary() {
            return isTemporary;
        }

        public void setIsTemporary(Boolean isTemporary) {
            this.isTemporary = isTemporary;
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

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }
    }
}