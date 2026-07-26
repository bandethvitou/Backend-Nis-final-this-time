package com.sarana_NIS.newTown_backend.dto.response.hr;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffAttendanceSummaryResponse {
    private Long staffId;
    private String staffName;
    private String department;
    private Integer presentDays;
    private Integer absentDays;
    private Integer lateDays;
    private Integer totalWorkingDays;
    private Double attendanceRate;
    private Integer overtimeHours;
    private List<MonthlyAttendance> monthly = new ArrayList<>();
    private List<DailyStatus> recentAttendance = new ArrayList<>();

    // Constructors
    public StaffAttendanceSummaryResponse() {}

    public StaffAttendanceSummaryResponse(Long staffId, String staffName) {
        this.staffId = staffId;
        this.staffName = staffName;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getPresentDays() { return presentDays; }
    public void setPresentDays(Integer presentDays) { this.presentDays = presentDays; }

    public Integer getAbsentDays() { return absentDays; }
    public void setAbsentDays(Integer absentDays) { this.absentDays = absentDays; }

    public Integer getLateDays() { return lateDays; }
    public void setLateDays(Integer lateDays) { this.lateDays = lateDays; }

    public Integer getTotalWorkingDays() { return totalWorkingDays; }
    public void setTotalWorkingDays(Integer totalWorkingDays) { this.totalWorkingDays = totalWorkingDays; }

    public Double getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(Double attendanceRate) { this.attendanceRate = attendanceRate; }

    public Integer getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(Integer overtimeHours) { this.overtimeHours = overtimeHours; }

    public List<MonthlyAttendance> getMonthly() { return monthly; }
    public void setMonthly(List<MonthlyAttendance> monthly) { this.monthly = monthly; }

    public List<DailyStatus> getRecentAttendance() { return recentAttendance; }
    public void setRecentAttendance(List<DailyStatus> recentAttendance) { this.recentAttendance = recentAttendance; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MonthlyAttendance {
        private String month;
        private Integer present;
        private Integer absent;
        private Integer late;
        private Double rate;

        // Constructors
        public MonthlyAttendance() {}

        // Getters and Setters
        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public Integer getPresent() { return present; }
        public void setPresent(Integer present) { this.present = present; }

        public Integer getAbsent() { return absent; }
        public void setAbsent(Integer absent) { this.absent = absent; }

        public Integer getLate() { return late; }
        public void setLate(Integer late) { this.late = late; }

        public Double getRate() { return rate; }
        public void setRate(Double rate) { this.rate = rate; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DailyStatus {
        private LocalDate date;
        private String status;
        private String checkIn;
        private String checkOut;
        private Integer hoursWorked;

        // Constructors
        public DailyStatus() {}

        // Getters and Setters
        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getCheckIn() { return checkIn; }
        public void setCheckIn(String checkIn) { this.checkIn = checkIn; }

        public String getCheckOut() { return checkOut; }
        public void setCheckOut(String checkOut) { this.checkOut = checkOut; }

        public Integer getHoursWorked() { return hoursWorked; }
        public void setHoursWorked(Integer hoursWorked) { this.hoursWorked = hoursWorked; }
    }
}