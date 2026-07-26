package com.sarana_NIS.newTown_backend.dto.response.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentWithStaffResponse {

    private DepartmentResponse department;
    private List<StaffDetail> staff = new ArrayList<>();
    private Integer totalStaff;
    private Integer activeStaff;
    private Integer onLeaveStaff;

    // Constructors
    public DepartmentWithStaffResponse() {}

    // Getters and Setters
    public DepartmentResponse getDepartment() { return department; }
    public void setDepartment(DepartmentResponse department) { this.department = department; }

    public List<StaffDetail> getStaff() { return staff; }
    public void setStaff(List<StaffDetail> staff) { this.staff = staff; }

    public Integer getTotalStaff() { return totalStaff; }
    public void setTotalStaff(Integer totalStaff) { this.totalStaff = totalStaff; }

    public Integer getActiveStaff() { return activeStaff; }
    public void setActiveStaff(Integer activeStaff) { this.activeStaff = activeStaff; }

    public Integer getOnLeaveStaff() { return onLeaveStaff; }
    public void setOnLeaveStaff(Integer onLeaveStaff) { this.onLeaveStaff = onLeaveStaff; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StaffDetail {
        private Long id;
        private String staffId;
        private String fullName;
        private String email;
        private String phone;
        private String position;
        private List<String> positions = new ArrayList<>();
        private String shift;
        private String status;
        private String joinDate;

        // Constructors
        public StaffDetail() {}

        public StaffDetail(Long id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getStaffId() { return staffId; }
        public void setStaffId(String staffId) { this.staffId = staffId; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }

        public List<String> getPositions() { return positions; }
        public void setPositions(List<String> positions) { this.positions = positions; }

        public String getShift() { return shift; }
        public void setShift(String shift) { this.shift = shift; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getJoinDate() { return joinDate; }
        public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
    }
}