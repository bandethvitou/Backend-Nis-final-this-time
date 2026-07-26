package com.sarana_NIS.newTown_backend.dto.response.admissions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import com.sarana_NIS.newTown_backend.model.enums.TourStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TourResponse {

    private Long id;  // ✅ ADD THIS
    private String parentName;
    private String studentName;
    private String phone;
    private String email;
    private Level level;
    private LocalDate tourDate;
    private LocalTime tourTime;
    private TourStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private String createdBy;

    // Constructors
    public TourResponse() {}

    public TourResponse(Long id, String parentName, LocalDate tourDate) {
        this.id = id;
        this.parentName = parentName;
        this.tourDate = tourDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public TourStatus getStatus() { return status; }
    public void setStatus(TourStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}