package com.sarana_NIS.newTown_backend.dto.response.it;

import com.sarana_NIS.newTown_backend.model.enums.EquipmentStatus;

import java.time.LocalDate;

public class EquipmentStatusResponse {
    private Long id;
    private String name;
    private String category;  // Changed from EquipmentCategory to String
    private String model;
    private String serialNumber;
    private String location;
    private EquipmentStatus status;
    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry;
    private Boolean isUnderWarranty;
    private Integer daysRemainingOnWarranty;
    private String statusColor;

    // Constructors
    public EquipmentStatusResponse() {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public LocalDate getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public void setWarrantyExpiry(LocalDate warrantyExpiry) {
        this.warrantyExpiry = warrantyExpiry;
    }

    public Boolean getIsUnderWarranty() {
        return isUnderWarranty;
    }

    public void setIsUnderWarranty(Boolean isUnderWarranty) {
        this.isUnderWarranty = isUnderWarranty;
    }

    public Integer getDaysRemainingOnWarranty() {
        return daysRemainingOnWarranty;
    }

    public void setDaysRemainingOnWarranty(Integer daysRemainingOnWarranty) {
        this.daysRemainingOnWarranty = daysRemainingOnWarranty;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }
}