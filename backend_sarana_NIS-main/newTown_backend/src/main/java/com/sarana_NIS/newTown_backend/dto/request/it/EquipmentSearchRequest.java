package com.sarana_NIS.newTown_backend.dto.request.it;

import com.sarana_NIS.newTown_backend.model.enums.EquipmentStatus;

public class EquipmentSearchRequest {

    private String keyword;
    private String category;
    private EquipmentStatus status;
    private String location;
    private String assignedTo;
    private Boolean hasWarranty;

    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "name";
    private String sortDirection = "ASC";

    // Constructors
    public EquipmentSearchRequest() {}

    public EquipmentSearchRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    // Getters and Setters
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public EquipmentStatus getStatus() { return status; }
    public void setStatus(EquipmentStatus status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    public Boolean getHasWarranty() { return hasWarranty; }
    public void setHasWarranty(Boolean hasWarranty) { this.hasWarranty = hasWarranty; }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public String getSortDirection() { return sortDirection; }
    public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }
}