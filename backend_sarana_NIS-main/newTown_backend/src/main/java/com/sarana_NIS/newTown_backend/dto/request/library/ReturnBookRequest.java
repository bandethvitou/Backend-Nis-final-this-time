package com.sarana_NIS.newTown_backend.dto.request.library;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReturnBookRequest {

    @NotNull(message = "Checkout ID is required")
    private Long checkoutId;

    private LocalDate returnDate;
    private BigDecimal fineAmount;
    private Boolean finePaid = false;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    private String condition;

    // Constructors
    public ReturnBookRequest() {}

    public ReturnBookRequest(Long checkoutId) {
        this.checkoutId = checkoutId;
    }

    // Getters and Setters
    public Long getCheckoutId() { return checkoutId; }
    public void setCheckoutId(Long checkoutId) { this.checkoutId = checkoutId; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public BigDecimal getFineAmount() { return fineAmount; }
    public void setFineAmount(BigDecimal fineAmount) { this.fineAmount = fineAmount; }

    public Boolean getFinePaid() { return finePaid; }
    public void setFinePaid(Boolean finePaid) { this.finePaid = finePaid; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}