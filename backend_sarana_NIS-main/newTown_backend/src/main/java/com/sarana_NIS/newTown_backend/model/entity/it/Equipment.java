package com.sarana_NIS.newTown_backend.model.entity.it;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.EquipmentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
public class Equipment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String serialNumber;

    @Column(length = 100)
    private String category;  // ✅ This field exists

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status = EquipmentStatus.AVAILABLE;  // ✅ This field exists

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;  // ✅ This field exists

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "warranty_expiry")
    private LocalDate warrantyExpiry;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @Column(name = "model", length = 100)
    private String model;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;  // ✅ This field exists

    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;  // ✅ This field exists

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "last_maintenance_date")
    private LocalDate lastMaintenanceDate;  // ✅ This field exists

    @Column(name = "next_maintenance_date")
    private LocalDate nextMaintenanceDate;  // ✅ This field exists

    @Column(columnDefinition = "TEXT")
    private String notes;  // ✅ This field exists

    @Column(name = "is_active")
    private Boolean isActive = true;  // ✅ This field exists

    // Constructors
    public Equipment() {
        this.status = EquipmentStatus.AVAILABLE;
        this.isActive = true;
    }

    public Equipment(String name, String serialNumber, String category) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.category = category;
        this.status = EquipmentStatus.AVAILABLE;
        this.isActive = true;
    }

    // Getters and Setters
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDate getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public void setWarrantyExpiry(LocalDate warrantyExpiry) {
        this.warrantyExpiry = warrantyExpiry;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public LocalDate getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }

    public void setNextMaintenanceDate(LocalDate nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // Helper methods
    public boolean isAvailable() {
        return this.status == EquipmentStatus.AVAILABLE;
    }

    public boolean isAssigned() {
        return this.status == EquipmentStatus.ASSIGNED;
    }

    public boolean isUnderMaintenance() {
        return this.status == EquipmentStatus.UNDER_MAINTENANCE;
    }

    public boolean isOutOfService() {
        return this.status == EquipmentStatus.OUT_OF_SERVICE;
    }

    public boolean isUnderWarranty() {
        return warrantyExpiry != null && warrantyExpiry.isAfter(LocalDate.now());
    }

    public boolean isMaintenanceOverdue() {
        return nextMaintenanceDate != null && nextMaintenanceDate.isBefore(LocalDate.now());
    }
}