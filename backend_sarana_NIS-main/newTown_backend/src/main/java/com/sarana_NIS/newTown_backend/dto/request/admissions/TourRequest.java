package com.sarana_NIS.newTown_backend.dto.request.admissions;

import com.sarana_NIS.newTown_backend.model.enums.Level;

import java.time.LocalDate;
import java.time.LocalTime;

public class TourRequest {

    private String parentName;
    private String studentName;
    private String phone;
    private String email;
    private Level level;
    private LocalDate tourDate;
    private LocalTime tourTime;
    private String notes;

    // Constructors
    public TourRequest() {}

    public TourRequest(String parentName, LocalDate tourDate) {
        this.parentName = parentName;
        this.tourDate = tourDate;
    }

    // Getters and Setters
    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public LocalDate getTourDate() { return tourDate; }
    public void setTourDate(LocalDate tourDate) { this.tourDate = tourDate; }

    public LocalTime getTourTime() { return tourTime; }
    public void setTourTime(LocalTime tourTime) { this.tourTime = tourTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}