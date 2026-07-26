package com.sarana_NIS.newTown_backend.dto.request.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class SchoolYearRequest {
    @NotBlank(message = "Year name is required")
    @Size(min = 2, max = 50, message = "Year name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private Boolean isCurrent;
    private List<TermRequest> terms;

    // Getters
    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public List<TermRequest> getTerms() {
        return terms;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public void setTerms(List<TermRequest> terms) {
        this.terms = terms;
    }

    public static class TermRequest {
        @NotBlank(message = "Term name is required")
        @Size(max = 50, message = "Term name must be less than 50 characters")
        private String name;

        @NotNull(message = "Term start date is required")
        private LocalDate startDate;

        @NotNull(message = "Term end date is required")
        private LocalDate endDate;

        private Boolean isCurrent;

        // Getters
        public String getName() {
            return name;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public Boolean getIsCurrent() {
            return isCurrent;
        }

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public void setIsCurrent(Boolean isCurrent) {
            this.isCurrent = isCurrent;
        }
    }
}