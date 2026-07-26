package com.sarana_NIS.newTown_backend.dto.response.finance;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeeSummaryResponse {

    private BigDecimal totalFees;
    private BigDecimal collected;
    private BigDecimal outstanding;
    private BigDecimal overdue;
    private List<FeeTypeSummary> byType = new ArrayList<>();
    private List<FeeStatusSummary> byStatus = new ArrayList<>();
    private List<FeeClassSummary> byClass = new ArrayList<>();
    private Integer totalStudents;
    private Integer defaultersCount;
    private Double collectionRate;

    // Constructors
    public FeeSummaryResponse() {}

    public FeeSummaryResponse(BigDecimal totalFees, BigDecimal collected) {
        this.totalFees = totalFees;
        this.collected = collected;
    }

    // Getters and Setters
    public BigDecimal getTotalFees() { return totalFees; }
    public void setTotalFees(BigDecimal totalFees) { this.totalFees = totalFees; }

    public BigDecimal getCollected() { return collected; }
    public void setCollected(BigDecimal collected) { this.collected = collected; }

    public BigDecimal getOutstanding() { return outstanding; }
    public void setOutstanding(BigDecimal outstanding) { this.outstanding = outstanding; }

    public BigDecimal getOverdue() { return overdue; }
    public void setOverdue(BigDecimal overdue) { this.overdue = overdue; }

    public List<FeeTypeSummary> getByType() { return byType; }
    public void setByType(List<FeeTypeSummary> byType) { this.byType = byType; }

    public List<FeeStatusSummary> getByStatus() { return byStatus; }
    public void setByStatus(List<FeeStatusSummary> byStatus) { this.byStatus = byStatus; }

    public List<FeeClassSummary> getByClass() { return byClass; }
    public void setByClass(List<FeeClassSummary> byClass) { this.byClass = byClass; }

    public Integer getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }

    public Integer getDefaultersCount() { return defaultersCount; }
    public void setDefaultersCount(Integer defaultersCount) { this.defaultersCount = defaultersCount; }

    public Double getCollectionRate() { return collectionRate; }
    public void setCollectionRate(Double collectionRate) { this.collectionRate = collectionRate; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FeeTypeSummary {
        private String type;
        private BigDecimal total;
        private BigDecimal collected;
        private BigDecimal outstanding;
        private BigDecimal overdue;
        private Double collectionRate;

        // Constructors
        public FeeTypeSummary() {}

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }

        public BigDecimal getCollected() { return collected; }
        public void setCollected(BigDecimal collected) { this.collected = collected; }

        public BigDecimal getOutstanding() { return outstanding; }
        public void setOutstanding(BigDecimal outstanding) { this.outstanding = outstanding; }

        public BigDecimal getOverdue() { return overdue; }
        public void setOverdue(BigDecimal overdue) { this.overdue = overdue; }

        public Double getCollectionRate() { return collectionRate; }
        public void setCollectionRate(Double collectionRate) { this.collectionRate = collectionRate; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FeeStatusSummary {
        private String status;
        private Integer count;
        private BigDecimal amount;
        private Double percentage;

        // Constructors
        public FeeStatusSummary() {}

        // Getters and Setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FeeClassSummary {
        private String className;
        private BigDecimal total;
        private BigDecimal collected;
        private BigDecimal outstanding;
        private Double collectionRate;

        // Constructors
        public FeeClassSummary() {}

        // Getters and Setters
        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }

        public BigDecimal getCollected() { return collected; }
        public void setCollected(BigDecimal collected) { this.collected = collected; }

        public BigDecimal getOutstanding() { return outstanding; }
        public void setOutstanding(BigDecimal outstanding) { this.outstanding = outstanding; }

        public Double getCollectionRate() { return collectionRate; }
        public void setCollectionRate(Double collectionRate) { this.collectionRate = collectionRate; }
    }
}