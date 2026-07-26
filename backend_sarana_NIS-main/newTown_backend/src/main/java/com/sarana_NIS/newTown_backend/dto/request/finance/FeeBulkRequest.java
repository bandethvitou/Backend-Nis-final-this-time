package com.sarana_NIS.newTown_backend.dto.request.finance;

import com.sarana_NIS.newTown_backend.model.enums.FeeCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeeBulkRequest {

    @NotNull(message = "Category is required")
    private FeeCategory category;  // ✅ Use category, not feeType

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @NotEmpty(message = "Student IDs cannot be empty")
    private List<Long> studentIds = new ArrayList<>();

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    // Constructors
    public FeeBulkRequest() {}

    public FeeBulkRequest(FeeCategory category, BigDecimal amount, LocalDate dueDate, List<Long> studentIds) {
        this.category = category;
        this.amount = amount;
        this.dueDate = dueDate;
        this.studentIds = studentIds;
    }

    // Getters and Setters
    public FeeCategory getCategory() {  // ✅ Use category
        return category;
    }

    public void setCategory(FeeCategory category) {  // ✅ Use category
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}