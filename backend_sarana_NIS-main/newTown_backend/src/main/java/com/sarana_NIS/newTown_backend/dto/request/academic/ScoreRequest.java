package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.NotNull;

public class ScoreRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Class Subject ID is required")
    private Long classSubjectId;

    @NotNull(message = "Term is required")
    private String term;

    private Double homeworkScore;
    private Double groupWorkScore;
    private Double attendanceScore;
    private Double activityScore;
    private Double multipleChoiceScore;
    private Double theoryScore;
    private Double practiceScore;

    private String comment;

    // Constructors
    public ScoreRequest() {}

    public ScoreRequest(Long studentId, Long classSubjectId, String term) {
        this.studentId = studentId;
        this.classSubjectId = classSubjectId;
        this.term = term;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getClassSubjectId() { return classSubjectId; }
    public void setClassSubjectId(Long classSubjectId) { this.classSubjectId = classSubjectId; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public Double getHomeworkScore() { return homeworkScore; }
    public void setHomeworkScore(Double homeworkScore) { this.homeworkScore = homeworkScore; }

    public Double getGroupWorkScore() { return groupWorkScore; }
    public void setGroupWorkScore(Double groupWorkScore) { this.groupWorkScore = groupWorkScore; }

    public Double getAttendanceScore() { return attendanceScore; }
    public void setAttendanceScore(Double attendanceScore) { this.attendanceScore = attendanceScore; }

    public Double getActivityScore() { return activityScore; }
    public void setActivityScore(Double activityScore) { this.activityScore = activityScore; }

    public Double getMultipleChoiceScore() { return multipleChoiceScore; }
    public void setMultipleChoiceScore(Double multipleChoiceScore) { this.multipleChoiceScore = multipleChoiceScore; }

    public Double getTheoryScore() { return theoryScore; }
    public void setTheoryScore(Double theoryScore) { this.theoryScore = theoryScore; }

    public Double getPracticeScore() { return practiceScore; }
    public void setPracticeScore(Double practiceScore) { this.practiceScore = practiceScore; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}