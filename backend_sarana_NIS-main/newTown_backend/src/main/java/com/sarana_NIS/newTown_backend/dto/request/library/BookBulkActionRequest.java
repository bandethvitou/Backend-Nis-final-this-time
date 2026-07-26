package com.sarana_NIS.newTown_backend.dto.request.library;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

public class BookBulkActionRequest {

    @NotEmpty(message = "Book IDs cannot be empty")
    private List<Long> bookIds = new ArrayList<>();

    @Pattern(regexp = "^(UPDATE_STATUS|DELETE|UPDATE_LOCATION|BULK_ADD)$",
            message = "Invalid action")
    private String action;

    private String newStatus;
    private String newLocation;
    private Integer quantityAdjustment;
    private String reason;

    // Constructors
    public BookBulkActionRequest() {}

    public BookBulkActionRequest(List<Long> bookIds, String action) {
        this.bookIds = bookIds;
        this.action = action;
    }

    // Getters and Setters
    public List<Long> getBookIds() { return bookIds; }
    public void setBookIds(List<Long> bookIds) { this.bookIds = bookIds; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }

    public String getNewLocation() { return newLocation; }
    public void setNewLocation(String newLocation) { this.newLocation = newLocation; }

    public Integer getQuantityAdjustment() { return quantityAdjustment; }
    public void setQuantityAdjustment(Integer quantityAdjustment) { this.quantityAdjustment = quantityAdjustment; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}