package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClassSubjectRequest {

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    private Long teacherId;

    @Size(max = 20, message = "Academic year must be less than 20 characters")
    private String academicYear;

    @Size(max = 20, message = "Term must be less than 20 characters")
    private String term;

    private String schedule;

    // Constructors
    public ClassSubjectRequest() {}

    public ClassSubjectRequest(Long classId, Long subjectId) {
        this.classId = classId;
        this.subjectId = subjectId;
    }

    // Getters and Setters
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
}