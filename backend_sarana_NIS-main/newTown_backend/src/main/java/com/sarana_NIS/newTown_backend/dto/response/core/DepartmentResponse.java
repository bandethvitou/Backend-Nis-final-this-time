package com.sarana_NIS.newTown_backend.dto.response.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentResponse {

    private Long id;
    private String name;
    private String description;
    private Long headUserId;
    private String headUserName;
    private String headUserEmail;
    private Integer staffCount;
    private List<StaffSummary> staff = new ArrayList<>();
    private List<String> positions = new ArrayList<>();
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    // Constructors
    public DepartmentResponse() {}

    public DepartmentResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getHeadUserId() { return headUserId; }
    public void setHeadUserId(Long headUserId) { this.headUserId = headUserId; }

    public String getHeadUserName() { return headUserName; }
    public void setHeadUserName(String headUserName) { this.headUserName = headUserName; }

    public String getHeadUserEmail() { return headUserEmail; }
    public void setHeadUserEmail(String headUserEmail) { this.headUserEmail = headUserEmail; }

    public Integer getStaffCount() { return staffCount; }
    public void setStaffCount(Integer staffCount) { this.staffCount = staffCount; }

    public List<StaffSummary> getStaff() { return staff; }
    public void setStaff(List<StaffSummary> staff) { this.staff = staff; }

    public List<String> getPositions() { return positions; }
    public void setPositions(List<String> positions) { this.positions = positions; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StaffSummary {
        private Long id;
        private String name;
        private String position;
        private String email;
        private String phone;

        // Constructors
        public StaffSummary() {}

        public StaffSummary(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }
}