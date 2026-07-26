package com.sarana_NIS.newTown_backend.dto.request.academic;

import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CheckinRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Date is required")
    private LocalDate checkinDate;

    private LocalTime checkinTime;
    private LocalTime checkoutTime;

    @NotNull(message = "Status is required")
    private AttendanceStatus status;

    @Size(max = 500, message = "Comment must be less than 500 characters")
    private String comment;

    private Boolean isBulkAction = false;
    private String bulkActionId;
    private List<BulkCheckin> bulkCheckins = new ArrayList<>();

    // Constructors
    public CheckinRequest() {}

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public LocalDate getCheckinDate() { return checkinDate; }
    public void setCheckinDate(LocalDate checkinDate) { this.checkinDate = checkinDate; }

    public LocalTime getCheckinTime() { return checkinTime; }
    public void setCheckinTime(LocalTime checkinTime) { this.checkinTime = checkinTime; }

    public LocalTime getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(LocalTime checkoutTime) { this.checkoutTime = checkoutTime; }

    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Boolean getIsBulkAction() { return isBulkAction; }
    public void setIsBulkAction(Boolean isBulkAction) { this.isBulkAction = isBulkAction; }

    public String getBulkActionId() { return bulkActionId; }
    public void setBulkActionId(String bulkActionId) { this.bulkActionId = bulkActionId; }

    public List<BulkCheckin> getBulkCheckins() { return bulkCheckins; }
    public void setBulkCheckins(List<BulkCheckin> bulkCheckins) { this.bulkCheckins = bulkCheckins; }

    // Inner class
    public static class BulkCheckin {
        @NotNull(message = "Student ID is required")
        private Long studentId;

        @NotNull(message = "Status is required")
        private AttendanceStatus status;

        @Size(max = 500, message = "Comment must be less than 500 characters")
        private String comment;

        // Constructors
        public BulkCheckin() {}

        // Getters and Setters
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public AttendanceStatus getStatus() { return status; }
        public void setStatus(AttendanceStatus status) { this.status = status; }

        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }
}