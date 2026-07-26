package com.sarana_NIS.newTown_backend.dto.response.it;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ITDashboardResponse {

    private Integer openTickets;
    private Integer totalEquipment;
    private Integer functionalEquipment;
    private Integer underRepair;
    private Integer maintenanceTasks;
    private Integer serversOnline;
    private Integer serversOffline;
    private List<TicketSummary> recentTickets = new ArrayList<>();
    private List<EquipmentSummary> equipmentStatus = new ArrayList<>();
    private List<MaintenanceSummary> upcomingMaintenance = new ArrayList<>();
    private Double systemUptime;

    // Constructors
    public ITDashboardResponse() {}

    // Getters and Setters
    public Integer getOpenTickets() { return openTickets; }
    public void setOpenTickets(Integer openTickets) { this.openTickets = openTickets; }

    public Integer getTotalEquipment() { return totalEquipment; }
    public void setTotalEquipment(Integer totalEquipment) { this.totalEquipment = totalEquipment; }

    public Integer getFunctionalEquipment() { return functionalEquipment; }
    public void setFunctionalEquipment(Integer functionalEquipment) { this.functionalEquipment = functionalEquipment; }

    public Integer getUnderRepair() { return underRepair; }
    public void setUnderRepair(Integer underRepair) { this.underRepair = underRepair; }

    public Integer getMaintenanceTasks() { return maintenanceTasks; }
    public void setMaintenanceTasks(Integer maintenanceTasks) { this.maintenanceTasks = maintenanceTasks; }

    public Integer getServersOnline() { return serversOnline; }
    public void setServersOnline(Integer serversOnline) { this.serversOnline = serversOnline; }

    public Integer getServersOffline() { return serversOffline; }
    public void setServersOffline(Integer serversOffline) { this.serversOffline = serversOffline; }

    public List<TicketSummary> getRecentTickets() { return recentTickets; }
    public void setRecentTickets(List<TicketSummary> recentTickets) { this.recentTickets = recentTickets; }

    public List<EquipmentSummary> getEquipmentStatus() { return equipmentStatus; }
    public void setEquipmentStatus(List<EquipmentSummary> equipmentStatus) { this.equipmentStatus = equipmentStatus; }

    public List<MaintenanceSummary> getUpcomingMaintenance() { return upcomingMaintenance; }
    public void setUpcomingMaintenance(List<MaintenanceSummary> upcomingMaintenance) { this.upcomingMaintenance = upcomingMaintenance; }

    public Double getSystemUptime() { return systemUptime; }
    public void setSystemUptime(Double systemUptime) { this.systemUptime = systemUptime; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TicketSummary {
        private Long id;
        private String title;
        private String category;
        private String priority;
        private String status;
        private String reportedBy;
        private LocalDateTime createdAt;

        // Constructors
        public TicketSummary() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getReportedBy() { return reportedBy; }
        public void setReportedBy(String reportedBy) { this.reportedBy = reportedBy; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EquipmentSummary {
        private String category;
        private Integer total;
        private Integer functional;
        private Integer underRepair;
        private Integer retired;

        // Constructors
        public EquipmentSummary() {}

        // Getters and Setters
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public Integer getTotal() { return total; }
        public void setTotal(Integer total) { this.total = total; }

        public Integer getFunctional() { return functional; }
        public void setFunctional(Integer functional) { this.functional = functional; }

        public Integer getUnderRepair() { return underRepair; }
        public void setUnderRepair(Integer underRepair) { this.underRepair = underRepair; }

        public Integer getRetired() { return retired; }
        public void setRetired(Integer retired) { this.retired = retired; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MaintenanceSummary {
        private Long id;
        private String equipmentName;
        private String issue;
        private LocalDate date;
        private String status;

        // Constructors
        public MaintenanceSummary() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getEquipmentName() { return equipmentName; }
        public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

        public String getIssue() { return issue; }
        public void setIssue(String issue) { this.issue = issue; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}