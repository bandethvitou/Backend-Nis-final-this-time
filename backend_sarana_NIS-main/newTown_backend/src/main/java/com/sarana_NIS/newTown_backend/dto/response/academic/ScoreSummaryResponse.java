package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreSummaryResponse {

    private Long studentId;
    private String studentFullName;
    private String studentName;
    private String subjectName;
    private Double totalScore;
    private String grade;
    private String comment;
    private Long classSubjectId;
    private String term;
    private Integer rank;
    private Double classAverage;
    private String status;

    // Summary Stats
    private Integer totalStudents;
    private Integer submittedStudents;
    private Double highestScore;
    private Double lowestScore;
    private Integer passCount;
    private Integer failCount;
    private Double passRate;

    // Constructors
    public ScoreSummaryResponse() {}

    public ScoreSummaryResponse(Long studentId, String studentName, Double totalScore) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.totalScore = totalScore;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentFullName() { return studentFullName; }
    public void setStudentFullName(String studentFullName) { this.studentFullName = studentFullName; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public Double getTotalScore() { return totalScore; }
    public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Long getClassSubjectId() { return classSubjectId; }
    public void setClassSubjectId(Long classSubjectId) { this.classSubjectId = classSubjectId; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }

    public Double getClassAverage() { return classAverage; }
    public void setClassAverage(Double classAverage) { this.classAverage = classAverage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }

    public Integer getSubmittedStudents() { return submittedStudents; }
    public void setSubmittedStudents(Integer submittedStudents) { this.submittedStudents = submittedStudents; }

    public Double getHighestScore() { return highestScore; }
    public void setHighestScore(Double highestScore) { this.highestScore = highestScore; }

    public Double getLowestScore() { return lowestScore; }
    public void setLowestScore(Double lowestScore) { this.lowestScore = lowestScore; }

    public Integer getPassCount() { return passCount; }
    public void setPassCount(Integer passCount) { this.passCount = passCount; }

    public Integer getFailCount() { return failCount; }
    public void setFailCount(Integer failCount) { this.failCount = failCount; }

    public Double getPassRate() { return passRate; }
    public void setPassRate(Double passRate) { this.passRate = passRate; }
}