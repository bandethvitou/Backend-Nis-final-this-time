package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreResponse {

    private Long id;

    // Student Information
    private Long studentId;
    private String studentName;

    // Class Subject Information
    private Long classSubjectId;
    private String classSubjectName;
    private String subjectName;
    private String teacherName;

    // Term Information
    private String term;

    // 7 Activity Scores
    private Double homeworkScore;
    private Double groupWorkScore;
    private Double attendanceScore;
    private Double activityScore;
    private Double multipleChoiceScore;
    private Double theoryScore;
    private Double practiceScore;

    // Calculated
    private Double totalScore;

    // Grading
    private String grade;
    private String gradeColor;
    private String status;

    // Metadata
    private String comment;
    private String submittedByName;
    private String approvedByName;
    private LocalDateTime submittedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ScoreResponse() {}

    public ScoreResponse(Long id, Long studentId, String studentName) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Long getClassSubjectId() { return classSubjectId; }
    public void setClassSubjectId(Long classSubjectId) { this.classSubjectId = classSubjectId; }

    public String getClassSubjectName() { return classSubjectName; }
    public void setClassSubjectName(String classSubjectName) { this.classSubjectName = classSubjectName; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

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

    public Double getTotalScore() { return totalScore; }
    public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getGradeColor() { return gradeColor; }
    public void setGradeColor(String gradeColor) { this.gradeColor = gradeColor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getSubmittedByName() { return submittedByName; }
    public void setSubmittedByName(String submittedByName) { this.submittedByName = submittedByName; }

    public String getApprovedByName() { return approvedByName; }
    public void setApprovedByName(String approvedByName) { this.approvedByName = approvedByName; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}