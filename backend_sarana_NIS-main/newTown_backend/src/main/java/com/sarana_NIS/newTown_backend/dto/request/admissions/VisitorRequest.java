package com.sarana_NIS.newTown_backend.dto.request.admissions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VisitorRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(max = 20, message = "Phone must be less than 20 characters")
    private String phone;

    @Size(max = 500, message = "Purpose must be less than 500 characters")
    private String purpose;

    private Long hostId;

    @Size(max = 100, message = "Organization must be less than 100 characters")
    private String organization;

    @Size(max = 20, message = "Vehicle number must be less than 20 characters")
    private String vehicleNumber;

    // Constructors
    public VisitorRequest() {}

    public VisitorRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public Long getHostId() { return hostId; }
    public void setHostId(Long hostId) { this.hostId = hostId; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
}