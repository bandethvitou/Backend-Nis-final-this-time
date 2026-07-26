package com.sarana_NIS.newTown_backend.dto.response.admissions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.VisitorStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitorResponse {

    private Long id;
    private String name;
    private String phone;
    private String purpose;
    private String hostName;
    private String hostDepartment;
    private String organization;
    private String vehicleNumber;
    private String passNumber;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private VisitorStatus status;
    private Boolean isActive;

    // Additional fields
    private String hostEmail;
    private String hostPhone;
    private String hostPosition;
    private Boolean isCheckedOut;
    private String duration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public VisitorResponse() {}

    public VisitorResponse(Long id, String name, String passNumber) {
        this.id = id;
        this.name = name;
        this.passNumber = passNumber;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }

    public String getHostDepartment() { return hostDepartment; }
    public void setHostDepartment(String hostDepartment) { this.hostDepartment = hostDepartment; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getPassNumber() { return passNumber; }
    public void setPassNumber(String passNumber) { this.passNumber = passNumber; }

    public LocalDateTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDateTime checkIn) { this.checkIn = checkIn; }

    public LocalDateTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDateTime checkOut) { this.checkOut = checkOut; }

    public VisitorStatus getStatus() { return status; }
    public void setStatus(VisitorStatus status) { this.status = status; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getHostEmail() { return hostEmail; }
    public void setHostEmail(String hostEmail) { this.hostEmail = hostEmail; }

    public String getHostPhone() { return hostPhone; }
    public void setHostPhone(String hostPhone) { this.hostPhone = hostPhone; }

    public String getHostPosition() { return hostPosition; }
    public void setHostPosition(String hostPosition) { this.hostPosition = hostPosition; }

    public Boolean getIsCheckedOut() { return isCheckedOut; }
    public void setIsCheckedOut(Boolean isCheckedOut) { this.isCheckedOut = isCheckedOut; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}