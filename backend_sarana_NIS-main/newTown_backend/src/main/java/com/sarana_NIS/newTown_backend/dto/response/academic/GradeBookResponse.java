package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GradeBookResponse {

    private String className;
    private String subjectName;
    private String teacherName;
    private String term;
    private List<StudentGradeBook> students = new ArrayList<>();
    private Map<String, Double> activityWeights = new HashMap<>();
    private List<String> columns = new ArrayList<>();
    private Double totalMaxScore;

    // Constructors
    public GradeBookResponse() {}

    public GradeBookResponse(String className, String subjectName, String term) {
        this.className = className;
        this.subjectName = subjectName;
        this.term = term;
    }

    // Getters and Setters
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public List<StudentGradeBook> getStudents() { return students; }
    public void setStudents(List<StudentGradeBook> students) { this.students = students; }

    public Map<String, Double> getActivityWeights() { return activityWeights; }
    public void setActivityWeights(Map<String, Double> activityWeights) { this.activityWeights = activityWeights; }

    public List<String> getColumns() { return columns; }
    public void setColumns(List<String> columns) { this.columns = columns; }

    public Double getTotalMaxScore() { return totalMaxScore; }
    public void setTotalMaxScore(Double totalMaxScore) { this.totalMaxScore = totalMaxScore; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StudentGradeBook {
        private Long studentId;
        private String studentName;
        private Map<String, Double> scores = new HashMap<>();
        private Double totalScore;
        private Double percentage;
        private String grade;
        private Integer rank;
        private String status;

        // Constructors
        public StudentGradeBook() {}

        public StudentGradeBook(Long studentId, String studentName) {
            this.studentId = studentId;
            this.studentName = studentName;
        }

        // Getters and Setters
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public Map<String, Double> getScores() { return scores; }
        public void setScores(Map<String, Double> scores) { this.scores = scores; }

        public Double getTotalScore() { return totalScore; }
        public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }

        public Integer getRank() { return rank; }
        public void setRank(Integer rank) { this.rank = rank; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}