package com.sarana_NIS.newTown_backend.dto.request.academic;

import com.sarana_NIS.newTown_backend.model.enums.Level;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ScoreStructureRequest {

    @NotNull(message = "Level is required")
    private Level level;

    private Long classId;
    private Long subjectId;

    @Size(max = 20, message = "Academic year must be less than 20 characters")
    private String academicYear;

    @Size(max = 20, message = "Term must be less than 20 characters")
    private String term;

    @DecimalMin(value = "0.0", message = "Homework max must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Homework max must be less than or equal to 10")
    private Double homeworkMax = 10.0;

    @DecimalMin(value = "0.0", message = "Homework weight must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Homework weight must be less than or equal to 100")
    private Double homeworkWeight = 10.0;

    @DecimalMin(value = "0.0", message = "Group work max must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Group work max must be less than or equal to 10")
    private Double groupWorkMax = 10.0;

    @DecimalMin(value = "0.0", message = "Group work weight must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Group work weight must be less than or equal to 100")
    private Double groupWorkWeight = 10.0;

    @DecimalMin(value = "0.0", message = "Attendance max must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Attendance max must be less than or equal to 10")
    private Double attendanceMax = 10.0;

    @DecimalMin(value = "0.0", message = "Attendance weight must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Attendance weight must be less than or equal to 100")
    private Double attendanceWeight = 10.0;

    @DecimalMin(value = "0.0", message = "Activity max must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Activity max must be less than or equal to 10")
    private Double activityMax = 10.0;

    @DecimalMin(value = "0.0", message = "Activity weight must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Activity weight must be less than or equal to 100")
    private Double activityWeight = 10.0;

    @DecimalMin(value = "0.0", message = "Multiple choice max must be greater than or equal to 0")
    @DecimalMax(value = "20.0", message = "Multiple choice max must be less than or equal to 20")
    private Double multipleChoiceMax = 20.0;

    @DecimalMin(value = "0.0", message = "Multiple choice weight must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Multiple choice weight must be less than or equal to 100")
    private Double multipleChoiceWeight = 20.0;

    @DecimalMin(value = "0.0", message = "Theory max must be greater than or equal to 0")
    @DecimalMax(value = "20.0", message = "Theory max must be less than or equal to 20")
    private Double theoryMax = 20.0;

    @DecimalMin(value = "0.0", message = "Theory weight must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Theory weight must be less than or equal to 100")
    private Double theoryWeight = 20.0;

    @DecimalMin(value = "0.0", message = "Practice max must be greater than or equal to 0")
    @DecimalMax(value = "20.0", message = "Practice max must be less than or equal to 20")
    private Double practiceMax = 20.0;

    @DecimalMin(value = "0.0", message = "Practice weight must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Practice weight must be less than or equal to 100")
    private Double practiceWeight = 20.0;

    private String gradingScale;

    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    // Constructors
    public ScoreStructureRequest() {}

    public ScoreStructureRequest(Level level, String academicYear, String term) {
        this.level = level;
        this.academicYear = academicYear;
        this.term = term;
    }

    // Getters and Setters
    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

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

    public String getGradingScale() { return gradingScale; }
    public void setGradingScale(String gradingScale) { this.gradingScale = gradingScale; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}