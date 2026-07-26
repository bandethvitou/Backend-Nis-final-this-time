package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.Level;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreStructureResponse {

    private Long id;
    private Level level;
    private Long classId;
    private String className;
    private Long subjectId;
    private String subjectName;
    private String academicYear;
    private String term;

    // 7 Activities Configuration
    private Double homeworkMax;
    private Double homeworkWeight;
    private Double groupWorkMax;
    private Double groupWorkWeight;
    private Double attendanceMax;
    private Double attendanceWeight;
    private Double activityMax;
    private Double activityWeight;
    private Double multipleChoiceMax;
    private Double multipleChoiceWeight;
    private Double theoryMax;
    private Double theoryWeight;
    private Double practiceMax;
    private Double practiceWeight;

    private Double totalMax;
    private Boolean isActive;
    private String gradingScale;
    private String notes;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;

    // Created By
    private Long createdById;
    private String createdByName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ScoreStructureResponse() {}

    public ScoreStructureResponse(Long id, Level level, String academicYear, String term) {
        this.id = id;
        this.level = level;
        this.academicYear = academicYear;
        this.term = term;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public Double getHomeworkMax() { return homeworkMax; }
    public void setHomeworkMax(Double homeworkMax) { this.homeworkMax = homeworkMax; }

    public Double getHomeworkWeight() { return homeworkWeight; }
    public void setHomeworkWeight(Double homeworkWeight) { this.homeworkWeight = homeworkWeight; }

    public Double getGroupWorkMax() { return groupWorkMax; }
    public void setGroupWorkMax(Double groupWorkMax) { this.groupWorkMax = groupWorkMax; }

    public Double getGroupWorkWeight() { return groupWorkWeight; }
    public void setGroupWorkWeight(Double groupWorkWeight) { this.groupWorkWeight = groupWorkWeight; }

    public Double getAttendanceMax() { return attendanceMax; }
    public void setAttendanceMax(Double attendanceMax) { this.attendanceMax = attendanceMax; }

    public Double getAttendanceWeight() { return attendanceWeight; }
    public void setAttendanceWeight(Double attendanceWeight) { this.attendanceWeight = attendanceWeight; }

    public Double getActivityMax() { return activityMax; }
    public void setActivityMax(Double activityMax) { this.activityMax = activityMax; }

    public Double getActivityWeight() { return activityWeight; }
    public void setActivityWeight(Double activityWeight) { this.activityWeight = activityWeight; }

    public Double getMultipleChoiceMax() { return multipleChoiceMax; }
    public void setMultipleChoiceMax(Double multipleChoiceMax) { this.multipleChoiceMax = multipleChoiceMax; }

    public Double getMultipleChoiceWeight() { return multipleChoiceWeight; }
    public void setMultipleChoiceWeight(Double multipleChoiceWeight) { this.multipleChoiceWeight = multipleChoiceWeight; }

    public Double getTheoryMax() { return theoryMax; }
    public void setTheoryMax(Double theoryMax) { this.theoryMax = theoryMax; }

    public Double getTheoryWeight() { return theoryWeight; }
    public void setTheoryWeight(Double theoryWeight) { this.theoryWeight = theoryWeight; }

    public Double getPracticeMax() { return practiceMax; }
    public void setPracticeMax(Double practiceMax) { this.practiceMax = practiceMax; }

    public Double getPracticeWeight() { return practiceWeight; }
    public void setPracticeWeight(Double practiceWeight) { this.practiceWeight = practiceWeight; }

    public Double getTotalMax() { return totalMax; }
    public void setTotalMax(Double totalMax) { this.totalMax = totalMax; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getGradingScale() { return gradingScale; }
    public void setGradingScale(String gradingScale) { this.gradingScale = gradingScale; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDateTime effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDateTime getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDateTime effectiveTo) { this.effectiveTo = effectiveTo; }

    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }

    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}