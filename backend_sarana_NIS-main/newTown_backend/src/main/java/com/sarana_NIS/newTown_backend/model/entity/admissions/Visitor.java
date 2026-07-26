package com.sarana_NIS.newTown_backend.model.entity.admissions;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.VisitorStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitors")
public class Visitor extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String purpose;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

    @Column(length = 100)
    private String organization;

    @Column(name = "vehicle_number", length = 20)
    private String vehicleNumber;

    @Column(name = "pass_number", unique = true, length = 20)
    private String passNumber;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Enumerated(EnumType.STRING)
    private VisitorStatus status = VisitorStatus.ACTIVE;

    // Constructors
    public Visitor() {}

    public Visitor(String name, String phone) {
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

    public User getHost() { return host; }
    public void setHost(User host) { this.host = host; }

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
}