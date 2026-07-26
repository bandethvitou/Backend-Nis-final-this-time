package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClassRequest {

    @NotBlank(message = "Class name is required")
    @Size(min = 1, max = 20, message = "Class name must be between 1 and 20 characters")
    private String name;

    @NotNull(message = "Level ID is required")
    private Long levelId;

    @Size(max = 10, message = "Grade must be less than 10 characters")
    private String grade;

    private Long teacherId;

    @Size(max = 20, message = "Academic year must be less than 20 characters")
    private String academicYear;

    @Size(max = 50, message = "Room must be less than 50 characters")
    private String room;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 100, message = "Capacity must be less than 100")
    private Integer capacity;

    private String schedule;

    // Constructors
    public ClassRequest() {}

    public ClassRequest(String name, Long levelId) {
        this.name = name;
        this.levelId = levelId;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getLevelId() { return levelId; }
    public void setLevelId(Long levelId) { this.levelId = levelId; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
}