package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentPerformanceResponse {

    private Long studentId;
    private String studentName;
    private Double overallGPA;
    private Integer rank;
    private String performanceLevel;
    private List<SubjectPerformance> subjects = new ArrayList<>();
    private TrendAnalysis trend;
    private List<Achievement> achievements = new ArrayList<>();
    private List<ImprovementArea> improvementAreas = new ArrayList<>();

    // Constructors
    public StudentPerformanceResponse() {}

    public StudentPerformanceResponse(Long studentId, String studentName, Double overallGPA) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.overallGPA = overallGPA;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Double getOverallGPA() { return overallGPA; }
    public void setOverallGPA(Double overallGPA) { this.overallGPA = overallGPA; }

    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }

    public String getPerformanceLevel() { return performanceLevel; }
    public void setPerformanceLevel(String performanceLevel) { this.performanceLevel = performanceLevel; }

    public List<SubjectPerformance> getSubjects() { return subjects; }
    public void setSubjects(List<SubjectPerformance> subjects) { this.subjects = subjects; }

    public TrendAnalysis getTrend() { return trend; }
    public void setTrend(TrendAnalysis trend) { this.trend = trend; }

    public List<Achievement> getAchievements() { return achievements; }
    public void setAchievements(List<Achievement> achievements) { this.achievements = achievements; }

    public List<ImprovementArea> getImprovementAreas() { return improvementAreas; }
    public void setImprovementAreas(List<ImprovementArea> improvementAreas) { this.improvementAreas = improvementAreas; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SubjectPerformance {
        private String subjectName;
        private Double score;
        private Double classAverage;
        private String grade;
        private String status;
        private Integer rank;

        // Constructors
        public SubjectPerformance() {}

        // Getters and Setters
        public String getSubjectName() { return subjectName; }
        public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

        public Double getScore() { return score; }
        public void setScore(Double score) { this.score = score; }

        public Double getClassAverage() { return classAverage; }
        public void setClassAverage(Double classAverage) { this.classAverage = classAverage; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Integer getRank() { return rank; }
        public void setRank(Integer rank) { this.rank = rank; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TrendAnalysis {
        private String trend;
        private List<Double> scores = new ArrayList<>();
        private List<String> periods = new ArrayList<>();
        private Double changePercentage;

        // Constructors
        public TrendAnalysis() {}

        // Getters and Setters
        public String getTrend() { return trend; }
        public void setTrend(String trend) { this.trend = trend; }

        public List<Double> getScores() { return scores; }
        public void setScores(List<Double> scores) { this.scores = scores; }

        public List<String> getPeriods() { return periods; }
        public void setPeriods(List<String> periods) { this.periods = periods; }

        public Double getChangePercentage() { return changePercentage; }
        public void setChangePercentage(Double changePercentage) { this.changePercentage = changePercentage; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Achievement {
        private String title;
        private String description;
        private LocalDate date;
        private String type;

        // Constructors
        public Achievement() {}

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ImprovementArea {
        private String area;
        private String recommendation;
        private String priority;

        // Constructors
        public ImprovementArea() {}

        // Getters and Setters
        public String getArea() { return area; }
        public void setArea(String area) { this.area = area; }

        public String getRecommendation() { return recommendation; }
        public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
    }
}