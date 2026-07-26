package com.sarana_NIS.newTown_backend.dto.response.finance;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetVsActualResponse {

    private String period;
    private List<BudgetItem> items = new ArrayList<>();
    private BigDecimal totalBudgeted;
    private BigDecimal totalActual;
    private BigDecimal variance;
    private Double variancePercentage;
    private String status;

    // Constructors
    public BudgetVsActualResponse() {}

    public BudgetVsActualResponse(String period) {
        this.period = period;
    }

    // Getters and Setters
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public List<BudgetItem> getItems() { return items; }
    public void setItems(List<BudgetItem> items) { this.items = items; }

    public BigDecimal getTotalBudgeted() { return totalBudgeted; }
    public void setTotalBudgeted(BigDecimal totalBudgeted) { this.totalBudgeted = totalBudgeted; }

    public BigDecimal getTotalActual() { return totalActual; }
    public void setTotalActual(BigDecimal totalActual) { this.totalActual = totalActual; }

    public BigDecimal getVariance() { return variance; }
    public void setVariance(BigDecimal variance) { this.variance = variance; }

    public Double getVariancePercentage() { return variancePercentage; }
    public void setVariancePercentage(Double variancePercentage) { this.variancePercentage = variancePercentage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BudgetItem {
        private String category;
        private BigDecimal budgeted;
        private BigDecimal actual;
        private BigDecimal variance;
        private Double variancePercentage;
        private String status;

        // Constructors
        public BudgetItem() {}

        // Getters and Setters
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public BigDecimal getBudgeted() { return budgeted; }
        public void setBudgeted(BigDecimal budgeted) { this.budgeted = budgeted; }

        public BigDecimal getActual() { return actual; }
        public void setActual(BigDecimal actual) { this.actual = actual; }

        public BigDecimal getVariance() { return variance; }
        public void setVariance(BigDecimal variance) { this.variance = variance; }

        public Double getVariancePercentage() { return variancePercentage; }
        public void setVariancePercentage(Double variancePercentage) { this.variancePercentage = variancePercentage; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}