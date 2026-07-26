package com.sarana_NIS.newTown_backend.dto.request.finance;

import com.sarana_NIS.newTown_backend.model.enums.FeeCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FeeRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Category is required")
    private FeeCategory category;  // ✅ Use category, not feeType

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    private String description;
    private String academicYear;
    private String term;

    // Constructors
    public FeeRequest() {
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}