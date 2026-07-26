package com.sarana_NIS.newTown_backend.dto.response.admissions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.ApplicationStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationResponse {

    private Long id;  // ✅ This field exists
    private String studentName;
    private String parentName;
    private String parentPhone;
    private String parentEmail;
    private LocalDate dob;
    private Level level;
    private String address;
    private String previousSchool;
    private String medicalNotes;

    private Long targetClassId;
    private String targetClassName;

    private ApplicationStatus status;

    private Long reviewedById;
    private String reviewedByName;

    private LocalDateTime inquiryDate;
    private LocalDateTime submittedDate;
    private LocalDateTime reviewedDate;
    private LocalDateTime approvedDate;
    private LocalDateTime enrolledDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ApplicationResponse() {}

    public ApplicationResponse(Long id, String studentName, String parentName) {
        this.id = id;
        this.studentName = studentName;
        this.parentName = parentName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPreviousSchool() { return previousSchool; }
    public void setPreviousSchool(String previousSchool) { this.previousSchool = previousSchool; }

    public String getMedicalNotes() { return medicalNotes; }
    public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }

    public Long getTargetClassId() { return targetClassId; }
    public void setTargetClassId(Long targetClassId) { this.targetClassId = targetClassId; }

    public String getTargetClassName() { return targetClassName; }
    public void setTargetClassName(String targetClassName) { this.targetClassName = targetClassName; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public Long getReviewedById() { return reviewedById; }
    public void setReviewedById(Long reviewedById) { this.reviewedById = reviewedById; }

    public String getReviewedByName() { return reviewedByName; }
    public void setReviewedByName(String reviewedByName) { this.reviewedByName = reviewedByName; }

    public LocalDateTime getInquiryDate() { return inquiryDate; }
    public void setInquiryDate(LocalDateTime inquiryDate) { this.inquiryDate = inquiryDate; }

    public LocalDateTime getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(LocalDateTime submittedDate) { this.submittedDate = submittedDate; }

    public LocalDateTime getReviewedDate() { return reviewedDate; }
    public void setReviewedDate(LocalDateTime reviewedDate) { this.reviewedDate = reviewedDate; }

    public LocalDateTime getApprovedDate() { return approvedDate; }
    public void setApprovedDate(LocalDateTime approvedDate) { this.approvedDate = approvedDate; }

    public LocalDateTime getEnrolledDate() { return enrolledDate; }
    public void setEnrolledDate(LocalDateTime enrolledDate) { this.enrolledDate = enrolledDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}