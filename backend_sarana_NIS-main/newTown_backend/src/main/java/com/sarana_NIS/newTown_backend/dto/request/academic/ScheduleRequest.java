package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class ScheduleRequest {

    private Long classId;
    private Long teacherId;
    private Long subjectId;
    private LocalDate date;

    @Pattern(regexp = "^(DAILY|WEEKLY|MONTHLY)$", message = "View must be DAILY, WEEKLY, or MONTHLY")
    private String view = "WEEKLY";

    @Pattern(regexp = "^(MORNING|AFTERNOON|EVENING)$", message = "Shift must be MORNING, AFTERNOON, or EVENING")
    private String shift;

    private Integer weekNumber;
    private Integer month;
    private Integer year;

    // Constructors
    public ScheduleRequest() {}

    // Getters and Setters
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getView() { return view; }
    public void setView(String view) { this.view = view; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public Integer getWeekNumber() { return weekNumber; }
    public void setWeekNumber(Integer weekNumber) { this.weekNumber = weekNumber; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}