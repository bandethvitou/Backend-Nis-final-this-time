package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GradeDistributionResponse {

    private String className;
    private String subjectName;
    private String term;
    private Integer totalStudents;
    private List<GradeBand> gradeBands = new ArrayList<>();
    private Statistics statistics;
    private List<StudentGrade> studentGrades = new ArrayList<>();

    // Constructors
    public GradeDistributionResponse() {}

    public GradeDistributionResponse(String className, String subjectName, String term) {
        this.className = className;
        this.subjectName = subjectName;
        this.term = term;
    }

    // Getters and Setters
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }

    public Integer getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }

    public List<GradeBand> getGradeBands() { return gradeBands; }
    public void setGradeBands(List<GradeBand> gradeBands) { this.gradeBands = gradeBands; }

    public Statistics getStatistics() { return statistics; }
    public void setStatistics(Statistics statistics) { this.statistics = statistics; }

    public List<StudentGrade> getStudentGrades() { return studentGrades; }
    public void setStudentGrades(List<StudentGrade> studentGrades) { this.studentGrades = studentGrades; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GradeBand {
        private String grade;
        private Integer count;
        private Double percentage;
        private String range;
        private String color;

        // Constructors
        public GradeBand() {}

        public GradeBand(String grade, Integer count) {
            this.grade = grade;
            this.count = count;
        }

        // Getters and Setters
        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }

        public String getRange() { return range; }
        public void setRange(String range) { this.range = range; }

        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Statistics {
        private Double mean;
        private Double median;
        private Double mode;
        private Double standardDeviation;
        private Double highestScore;
        private Double lowestScore;
        private Integer passCount;
        private Integer failCount;
        private Double passRate;

        // Constructors
        public Statistics() {}

        // Getters and Setters
        public Double getMean() { return mean; }
        public void setMean(Double mean) { this.mean = mean; }

        public Double getMedian() { return median; }
        public void setMedian(Double median) { this.median = median; }

        public Double getMode() { return mode; }
        public void setMode(Double mode) { this.mode = mode; }

        public Double getStandardDeviation() { return standardDeviation; }
        public void setStandardDeviation(Double standardDeviation) { this.standardDeviation = standardDeviation; }

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StudentGrade {
        private Long studentId;
        private String studentName;
        private Double totalScore;
        private String grade;
        private Integer rank;

        // Constructors
        public StudentGrade() {}

        public StudentGrade(Long studentId, String studentName, Double totalScore) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.totalScore = totalScore;
        }

        // Getters and Setters
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public Double getTotalScore() { return totalScore; }
        public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }

        public Integer getRank() { return rank; }
        public void setRank(Integer rank) { this.rank = rank; }
    }
}