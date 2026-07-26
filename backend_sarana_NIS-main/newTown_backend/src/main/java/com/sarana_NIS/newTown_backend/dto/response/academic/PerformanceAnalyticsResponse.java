package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerformanceAnalyticsResponse {

    private String studentName;
    private String studentId;
    private String className;
    private String level;
    private Double overallGPA;
    private Integer rank;
    private List<SubjectPerformance> subjects = new ArrayList<>();
    private List<ActivityPerformance> activities = new ArrayList<>();
    private TrendData trend;
    private String strengths;
    private String weaknesses;
    private List<String> recommendations = new ArrayList<>();

    // Constructors
    public PerformanceAnalyticsResponse() {}

    public PerformanceAnalyticsResponse(String studentName, String studentId) {
        this.studentName = studentName;
        this.studentId = studentId;
    }

    // Getters and Setters
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public Double getOverallGPA() { return overallGPA; }
    public void setOverallGPA(Double overallGPA) { this.overallGPA = overallGPA; }

    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }

    public List<SubjectPerformance> getSubjects() { return subjects; }
    public void setSubjects(List<SubjectPerformance> subjects) { this.subjects = subjects; }

    public List<ActivityPerformance> getActivities() { return activities; }
    public void setActivities(List<ActivityPerformance> activities) { this.activities = activities; }

    public TrendData getTrend() { return trend; }
    public void setTrend(TrendData trend) { this.trend = trend; }

    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }

    public String getWeaknesses() { return weaknesses; }
    public void setWeaknesses(String weaknesses) { this.weaknesses = weaknesses; }

    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SubjectPerformance {
        private String subjectName;
        private Double score;
        private Double classAverage;
        private Double topScore;
        private String grade;
        private String status;

        // Constructors
        public SubjectPerformance() {}

        public SubjectPerformance(String subjectName, Double score) {
            this.subjectName = subjectName;
            this.score = score;
        }

        // Getters and Setters
        public String getSubjectName() { return subjectName; }
        public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

        public Double getScore() { return score; }
        public void setScore(Double score) { this.score = score; }

        public Double getClassAverage() { return classAverage; }
        public void setClassAverage(Double classAverage) { this.classAverage = classAverage; }

        public Double getTopScore() { return topScore; }
        public void setTopScore(Double topScore) { this.topScore = topScore; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ActivityPerformance {
        private String activity;
        private Double studentScore;
        private Double maxScore;
        private Double classAverage;
        private Double percentage;

        // Constructors
        public ActivityPerformance() {}

        // Getters and Setters
        public String getActivity() { return activity; }
        public void setActivity(String activity) { this.activity = activity; }

        public Double getStudentScore() { return studentScore; }
        public void setStudentScore(Double studentScore) { this.studentScore = studentScore; }

        public Double getMaxScore() { return maxScore; }
        public void setMaxScore(Double maxScore) { this.maxScore = maxScore; }

        public Double getClassAverage() { return classAverage; }
        public void setClassAverage(Double classAverage) { this.classAverage = classAverage; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TrendData {
        private List<Double> scores = new ArrayList<>();
        private List<String> periods = new ArrayList<>();
        private String trend;
        private Double changePercentage;

        // Constructors
        public TrendData() {}

        // Getters and Setters
        public List<Double> getScores() { return scores; }
        public void setScores(List<Double> scores) { this.scores = scores; }

        public List<String> getPeriods() { return periods; }
        public void setPeriods(List<String> periods) { this.periods = periods; }

        public String getTrend() { return trend; }
        public void setTrend(String trend) { this.trend = trend; }

        public Double getChangePercentage() { return changePercentage; }
        public void setChangePercentage(Double changePercentage) { this.changePercentage = changePercentage; }
    }
}