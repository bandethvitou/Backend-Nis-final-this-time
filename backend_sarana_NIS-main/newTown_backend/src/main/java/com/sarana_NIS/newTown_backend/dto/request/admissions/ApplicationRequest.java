package com.sarana_NIS.newTown_backend.dto.request.admissions;

import com.sarana_NIS.newTown_backend.model.enums.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ApplicationRequest {

    @NotBlank(message = "Student name is required")
    @Size(max = 100, message = "Student name must be less than 100 characters")
    private String studentName;

    @NotBlank(message = "Parent name is required")
    @Size(max = 100, message = "Parent name must be less than 100 characters")
    private String parentName;

    @NotBlank(message = "Parent phone is required")
    @Size(max = 20, message = "Phone must be less than 20 characters")
    private String parentPhone;

    @Size(max = 100, message = "Email must be less than 100 characters")
    private String parentEmail;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotNull(message = "Level is required")
    private Level level;

    private Long classId;
    private String address;
    private String previousSchool;
    private String medicalNotes;

    // Constructors
    public ApplicationRequest() {}

    public ApplicationRequest(String studentName, String parentName, String parentPhone, Level level) {
        this.studentName = studentName;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.level = level;
    }

    // Getters and Setters
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

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPreviousSchool() { return previousSchool; }
    public void setPreviousSchool(String previousSchool) { this.previousSchool = previousSchool; }

    public String getMedicalNotes() { return medicalNotes; }
    public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }
}