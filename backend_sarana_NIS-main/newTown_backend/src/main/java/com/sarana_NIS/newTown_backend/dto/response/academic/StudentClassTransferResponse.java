package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentClassTransferResponse {

    private Long studentId;
    private String studentName;
    private String previousClass;
    private String newClass;
    private LocalDateTime transferDate;
    private String reason;
    private String performedBy;
    private Boolean success;
    private String message;
    private List<String> warnings = new ArrayList<>();

    // Constructors
    public StudentClassTransferResponse() {}

    public StudentClassTransferResponse(Long studentId, String studentName, Boolean success) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.success = success;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getPreviousClass() { return previousClass; }
    public void setPreviousClass(String previousClass) { this.previousClass = previousClass; }

    public String getNewClass() { return newClass; }
    public void setNewClass(String newClass) { this.newClass = newClass; }

    public LocalDateTime getTransferDate() { return transferDate; }
    public void setTransferDate(LocalDateTime transferDate) { this.transferDate = transferDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<String> getWarnings() { return warnings; }
    public void setWarnings(List<String> warnings) { this.warnings = warnings; }
}