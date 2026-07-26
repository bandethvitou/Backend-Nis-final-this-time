package com.sarana_NIS.newTown_backend.dto.request.academic;

import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Class Subject ID is required")
    private Long classSubjectId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Status is required")
    private AttendanceStatus status;

    private LocalTime timeIn;
    private LocalTime timeOut;

    @Size(max = 500, message = "Comment must be less than 500 characters")
    private String comment;

    private Boolean isBulk = false;
    private List<BulkAttendance> attendances = new ArrayList<>();

    // Constructors
    public AttendanceRequest() {}

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getClassSubjectId() { return classSubjectId; }
    public void setClassSubjectId(Long classSubjectId) { this.classSubjectId = classSubjectId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }

    public LocalTime getTimeIn() { return timeIn; }
    public void setTimeIn(LocalTime timeIn) { this.timeIn = timeIn; }

    public LocalTime getTimeOut() { return timeOut; }
    public void setTimeOut(LocalTime timeOut) { this.timeOut = timeOut; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Boolean getIsBulk() { return isBulk; }
    public void setIsBulk(Boolean isBulk) { this.isBulk = isBulk; }

    public List<BulkAttendance> getAttendances() { return attendances; }
    public void setAttendances(List<BulkAttendance> attendances) { this.attendances = attendances; }

    // Inner class
    public static class BulkAttendance {
        @NotNull(message = "Student ID is required")
        private Long studentId;

        @NotNull(message = "Status is required")
        private AttendanceStatus status;

        @Size(max = 500, message = "Comment must be less than 500 characters")
        private String comment;

        // Constructors
        public BulkAttendance() {}

        public BulkAttendance(Long studentId, AttendanceStatus status) {
            this.studentId = studentId;
            this.status = status;
        }

        // Getters and Setters
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public AttendanceStatus getStatus() { return status; }
        public void setStatus(AttendanceStatus status) { this.status = status; }

        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }
}