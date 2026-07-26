package com.sarana_NIS.newTown_backend.dto.response.admissions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationStatsResponse {

    private Integer totalRegistrations;
    private Integer newStudents;
    private Integer droppedStudents;
    private Integer transferredStudents;
    private List<LevelRegistration> byLevel = new ArrayList<>();
    private List<MonthlyRegistration> monthly = new ArrayList<>();
    private List<ClassRegistration> byClass = new ArrayList<>();
    private Double dropOutRate;
    private Double retentionRate;

    // Constructors
    public RegistrationStatsResponse() {}

    public RegistrationStatsResponse(Integer totalRegistrations, Integer newStudents) {
        this.totalRegistrations = totalRegistrations;
        this.newStudents = newStudents;
    }

    // Getters and Setters
    public Integer getTotalRegistrations() { return totalRegistrations; }
    public void setTotalRegistrations(Integer totalRegistrations) { this.totalRegistrations = totalRegistrations; }

    public Integer getNewStudents() { return newStudents; }
    public void setNewStudents(Integer newStudents) { this.newStudents = newStudents; }

    public Integer getDroppedStudents() { return droppedStudents; }
    public void setDroppedStudents(Integer droppedStudents) { this.droppedStudents = droppedStudents; }

    public Integer getTransferredStudents() { return transferredStudents; }
    public void setTransferredStudents(Integer transferredStudents) { this.transferredStudents = transferredStudents; }

    public List<LevelRegistration> getByLevel() { return byLevel; }
    public void setByLevel(List<LevelRegistration> byLevel) { this.byLevel = byLevel; }

    public List<MonthlyRegistration> getMonthly() { return monthly; }
    public void setMonthly(List<MonthlyRegistration> monthly) { this.monthly = monthly; }

    public List<ClassRegistration> getByClass() { return byClass; }
    public void setByClass(List<ClassRegistration> byClass) { this.byClass = byClass; }

    public Double getDropOutRate() { return dropOutRate; }
    public void setDropOutRate(Double dropOutRate) { this.dropOutRate = dropOutRate; }

    public Double getRetentionRate() { return retentionRate; }
    public void setRetentionRate(Double retentionRate) { this.retentionRate = retentionRate; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LevelRegistration {
        private String level;
        private Integer count;
        private Double percentage;

        // Constructors
        public LevelRegistration() {}

        public LevelRegistration(String level, Integer count) {
            this.level = level;
            this.count = count;
        }

        // Getters and Setters
        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MonthlyRegistration {
        private String month;
        private Integer newStudents;
        private Integer dropouts;
        private Integer netChange;

        // Constructors
        public MonthlyRegistration() {}

        // Getters and Setters
        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public Integer getNewStudents() { return newStudents; }
        public void setNewStudents(Integer newStudents) { this.newStudents = newStudents; }

        public Integer getDropouts() { return dropouts; }
        public void setDropouts(Integer dropouts) { this.dropouts = dropouts; }

        public Integer getNetChange() { return netChange; }
        public void setNetChange(Integer netChange) { this.netChange = netChange; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClassRegistration {
        private String className;
        private Integer count;
        private Integer capacity;
        private Double occupancyRate;

        // Constructors
        public ClassRegistration() {}

        public ClassRegistration(String className, Integer count, Integer capacity) {
            this.className = className;
            this.count = count;
            this.capacity = capacity;
        }

        // Getters and Setters
        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Integer getCapacity() { return capacity; }
        public void setCapacity(Integer capacity) { this.capacity = capacity; }

        public Double getOccupancyRate() { return occupancyRate; }
        public void setOccupancyRate(Double occupancyRate) { this.occupancyRate = occupancyRate; }
    }
}