package com.sarana_NIS.newTown_backend.dto.response.finance;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FinanceDashboardResponse {

    private BigDecimal totalRevenue;
    private BigDecimal totalExpenses;
    private BigDecimal netProfit;
    private BigDecimal collectionRate;
    private Integer defaultersCount;
    private List<RevenueTrend> revenueTrend = new ArrayList<>();
    private List<ExpenseTrend> expenseTrend = new ArrayList<>();
    private List<FeeAlert> feeAlerts = new ArrayList<>();
    private BudgetStatus budgetStatus;

    // Constructors
    public FinanceDashboardResponse() {}

    // Getters and Setters
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public BigDecimal getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(BigDecimal totalExpenses) { this.totalExpenses = totalExpenses; }

    public BigDecimal getNetProfit() { return netProfit; }
    public void setNetProfit(BigDecimal netProfit) { this.netProfit = netProfit; }

    public BigDecimal getCollectionRate() { return collectionRate; }
    public void setCollectionRate(BigDecimal collectionRate) { this.collectionRate = collectionRate; }

    public Integer getDefaultersCount() { return defaultersCount; }
    public void setDefaultersCount(Integer defaultersCount) { this.defaultersCount = defaultersCount; }

    public List<RevenueTrend> getRevenueTrend() { return revenueTrend; }
    public void setRevenueTrend(List<RevenueTrend> revenueTrend) { this.revenueTrend = revenueTrend; }

    public List<ExpenseTrend> getExpenseTrend() { return expenseTrend; }
    public void setExpenseTrend(List<ExpenseTrend> expenseTrend) { this.expenseTrend = expenseTrend; }

    public List<FeeAlert> getFeeAlerts() { return feeAlerts; }
    public void setFeeAlerts(List<FeeAlert> feeAlerts) { this.feeAlerts = feeAlerts; }

    public BudgetStatus getBudgetStatus() { return budgetStatus; }
    public void setBudgetStatus(BudgetStatus budgetStatus) { this.budgetStatus = budgetStatus; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RevenueTrend {
        private String month;
        private BigDecimal fees;
        private BigDecimal otherIncome;
        private BigDecimal total;

        // Constructors
        public RevenueTrend() {}

        // Getters and Setters
        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public BigDecimal getFees() { return fees; }
        public void setFees(BigDecimal fees) { this.fees = fees; }

        public BigDecimal getOtherIncome() { return otherIncome; }
        public void setOtherIncome(BigDecimal otherIncome) { this.otherIncome = otherIncome; }

        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ExpenseTrend {
        private String month;
        private BigDecimal salaries;
        private BigDecimal utilities;
        private BigDecimal maintenance;
        private BigDecimal other;
        private BigDecimal total;

        // Constructors
        public ExpenseTrend() {}

        // Getters and Setters
        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public BigDecimal getSalaries() { return salaries; }
        public void setSalaries(BigDecimal salaries) { this.salaries = salaries; }

        public BigDecimal getUtilities() { return utilities; }
        public void setUtilities(BigDecimal utilities) { this.utilities = utilities; }

        public BigDecimal getMaintenance() { return maintenance; }
        public void setMaintenance(BigDecimal maintenance) { this.maintenance = maintenance; }

        public BigDecimal getOther() { return other; }
        public void setOther(BigDecimal other) { this.other = other; }

        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FeeAlert {
        private String studentName;
        private String className;
        private BigDecimal amount;
        private Integer daysOverdue;
        private String status;

        // Constructors
        public FeeAlert() {}

        // Getters and Setters
        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }

        public Integer getDaysOverdue() { return daysOverdue; }
        public void setDaysOverdue(Integer daysOverdue) { this.daysOverdue = daysOverdue; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BudgetStatus {
        private BigDecimal budgeted;
        private BigDecimal actual;
        private BigDecimal variance;
        private Double percentageUsed;
        private String status;

        // Constructors
        public BudgetStatus() {}

        // Getters and Setters
        public BigDecimal getBudgeted() { return budgeted; }
        public void setBudgeted(BigDecimal budgeted) { this.budgeted = budgeted; }

        public BigDecimal getActual() { return actual; }
        public void setActual(BigDecimal actual) { this.actual = actual; }

        public BigDecimal getVariance() { return variance; }
        public void setVariance(BigDecimal variance) { this.variance = variance; }

        public Double getPercentageUsed() { return percentageUsed; }
        public void setPercentageUsed(Double percentageUsed) { this.percentageUsed = percentageUsed; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}