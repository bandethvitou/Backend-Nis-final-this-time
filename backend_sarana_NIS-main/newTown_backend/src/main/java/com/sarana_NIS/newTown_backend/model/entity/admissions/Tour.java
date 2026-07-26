package com.sarana_NIS.newTown_backend.model.entity.admissions;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import com.sarana_NIS.newTown_backend.model.enums.TourStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tours")
public class Tour extends BaseEntity {  // ✅ Inherits id from BaseEntity

    @Column(name = "parent_name", nullable = false, length = 100)
    private String parentName;

    @Column(name = "student_name", length = 100)
    private String studentName;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "tour_date")
    private LocalDate tourDate;

    @Column(name = "tour_time")
    private LocalTime tourTime;

    @Enumerated(EnumType.STRING)
    private TourStatus status = TourStatus.SCHEDULED;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Constructors
    public Tour() {}

    public Tour(String parentName, LocalDate tourDate) {
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

    public TourStatus getStatus() { return status; }
    public void setStatus(TourStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}