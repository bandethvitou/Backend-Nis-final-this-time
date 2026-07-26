package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentCheckinResponse {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long classId;
    private String className;
    private LocalDate checkinDate;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private AttendanceStatus status;
    private String comment;
    private String markedByName;
    private Boolean isBulkAction;
    private String bulkActionId;
    private Integer lateMinutes;
    private Integer earlyDepartureMinutes;

    // Constructors
    public StudentCheckinResponse() {}

    public StudentCheckinResponse(Long id, Long studentId, String studentName) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

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

    public String getMarkedByName() { return markedByName; }
    public void setMarkedByName(String markedByName) { this.markedByName = markedByName; }

    public Boolean getIsBulkAction() { return isBulkAction; }
    public void setIsBulkAction(Boolean isBulkAction) { this.isBulkAction = isBulkAction; }

    public String getBulkActionId() { return bulkActionId; }
    public void setBulkActionId(String bulkActionId) { this.bulkActionId = bulkActionId; }

    public Integer getLateMinutes() { return lateMinutes; }
    public void setLateMinutes(Integer lateMinutes) { this.lateMinutes = lateMinutes; }

    public Integer getEarlyDepartureMinutes() { return earlyDepartureMinutes; }
    public void setEarlyDepartureMinutes(Integer earlyDepartureMinutes) { this.earlyDepartureMinutes = earlyDepartureMinutes; }
}