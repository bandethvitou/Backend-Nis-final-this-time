package com.sarana_NIS.newTown_backend.dto.request.user;

import com.sarana_NIS.newTown_backend.model.enums.EnrollmentStatus;
import com.sarana_NIS.newTown_backend.model.enums.Gender;
import com.sarana_NIS.newTown_backend.model.enums.Level;

import java.time.LocalDate;
import java.util.List;

public class StudentUpdateRequest {

    private String nameKhmer;
    private String nameEnglish;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Level level;
    private EnrollmentStatus studentStatus;
    private String medicalNotes;
    private String previousSchool;
    private Long classId;
    private List<Long> parentIds;

    // Constructors
    public StudentUpdateRequest() {
    }

    // Getters and Setters
    public String getNameKhmer() {
        return nameKhmer;
    }

    public void setNameKhmer(String nameKhmer) {
        this.nameKhmer = nameKhmer;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public EnrollmentStatus getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(EnrollmentStatus studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getMedicalNotes() {
        return medicalNotes;
    }

    public void setMedicalNotes(String medicalNotes) {
        this.medicalNotes = medicalNotes;
    }

    public String getPreviousSchool() {
        return previousSchool;
    }

    public void setPreviousSchool(String previousSchool) {
        this.previousSchool = previousSchool;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }
}