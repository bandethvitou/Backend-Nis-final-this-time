package com.sarana_NIS.newTown_backend.dto.response.it;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaintenanceScheduleResponse {

    private Integer scheduledToday;
    private Integer inProgress;
    private Integer completed;
    private Integer overdue;
    private List<MaintenanceTask> tasks = new ArrayList<>();

    // Constructors
    public MaintenanceScheduleResponse() {}

    public MaintenanceScheduleResponse(Integer scheduledToday, Integer inProgress, Integer completed) {
        this.scheduledToday = scheduledToday;
        this.inProgress = inProgress;
        this.completed = completed;
    }

    // Getters and Setters
    public Integer getScheduledToday() { return scheduledToday; }
    public void setScheduledToday(Integer scheduledToday) { this.scheduledToday = scheduledToday; }

    public Integer getInProgress() { return inProgress; }
    public void setInProgress(Integer inProgress) { this.inProgress = inProgress; }

    public Integer getCompleted() { return completed; }
    public void setCompleted(Integer completed) { this.completed = completed; }

    public Integer getOverdue() { return overdue; }
    public void setOverdue(Integer overdue) { this.overdue = overdue; }

    public List<MaintenanceTask> getTasks() { return tasks; }
    public void setTasks(List<MaintenanceTask> tasks) { this.tasks = tasks; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MaintenanceTask {
        private Long id;
        private String equipmentName;
        private String issue;
        private String status;
        private LocalDate maintenanceDate;
        private String technician;
        private String priority;

        // Constructors
        public MaintenanceTask() {}

        public MaintenanceTask(Long id, String equipmentName, String issue) {
            this.id = id;
            this.equipmentName = equipmentName;
            this.issue = issue;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getEquipmentName() { return equipmentName; }
        public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

        public String getIssue() { return issue; }
        public void setIssue(String issue) { this.issue = issue; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDate getMaintenanceDate() { return maintenanceDate; }
        public void setMaintenanceDate(LocalDate maintenanceDate) { this.maintenanceDate = maintenanceDate; }

        public String getTechnician() { return technician; }
        public void setTechnician(String technician) { this.technician = technician; }

        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
    }
}